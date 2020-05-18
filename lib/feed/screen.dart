import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/access/banner.dart';
import 'package:starrit/common/config.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/common/navigation.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/feed/widget/post.dart';
import 'package:starrit/search/screen.dart';
import 'package:starrit/settings/actions.dart';
import 'package:visibility_detector/visibility_detector.dart';
import 'package:starrit/access/model/access.dart';

class FeedScreen extends StatelessWidget {
  /// Shows data for this feed.
  final Feed feed;

  final ScrollController _scrollController = ScrollController();

  FeedScreen(this.feed) : assert(feed != null);

  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        onDispose: (store) async {
          _scrollController.dispose();
          store.dispatch(DisposeFeedData(feed));
        },
        distinct: true,
        converter: (store) => _ViewModel.build(store, feed),
        onInitialBuild: (viewModel) {
          if (viewModel.shouldLoad(0)) viewModel.load();
        },
        builder: (context, viewModel) => VisibilityDetector(
          key: ObjectKey(feed),
          onVisibilityChanged: viewModel.updateLatestFeed,
          child: Scaffold(
            appBar: AppBar(
              automaticallyImplyLeading: false,
              title: Text('$feed'),
              actions: [
                IconButton(
                  icon: Icon(Icons.arrow_upward),
                  onPressed: () {
                    _scrollController.jumpTo(0);
                    viewModel.load(reset: true);
                  },
                ),
                IconButton(
                  icon: Icon(viewModel.blurNsfw
                      ? Icons.visibility_off
                      : Icons.visibility),
                  onPressed: viewModel.toggleBlurNsfw,
                ),
                IconButton(
                  icon: Icon(Icons.search),
                  onPressed: viewModel.openSearchScreen,
                ),
              ],
            ),
            body: ListView.separated(
              controller: _scrollController,
              itemCount: viewModel.itemCount,
              separatorBuilder: (context, index) => Divider(height: 1),
              itemBuilder: (context, index) => _item(context, viewModel, index),
            ),
          ),
        ),
      );

  Widget _item(BuildContext context, _ViewModel viewModel, int index) {
    if (viewModel.shouldLoad(index)) viewModel.load();

    if (viewModel.maybeUpdateAccess && index == 0) {
      return AccessBanner();
    }
    if (index < viewModel.postCount) {
      return PostView(viewModel.posts[index]);
    }
    if (viewModel.hasError) {
      return Container(
        padding: EdgeInsets.symmetric(horizontal: 16),
        child: Row(
          children: <Widget>[
            Expanded(
              child: Text(
                viewModel.errorMessage,
                overflow: TextOverflow.ellipsis,
              ),
            ),
            FlatButton(
              onPressed: () => viewModel.load(),
              textTheme: ButtonTextTheme.primary,
              child: Text('RETRY'),
            ),
          ],
        ),
      );
    }
    return LinearProgressIndicator();
  }
}

@immutable
class _ViewModel {
  /// Determines how many items may left unseen before request new chunk.
  static const loadThreshold = 10;

  /// Redux-store.
  final Store<AppState> store;

  /// Data belongs to this feed.
  final Feed feed;

  /// Current feed state. Nullable.
  final FeedState state;

  /// Access status.
  final Access access;

  /// Whether NSFW content should be blurred.
  final bool blurNsfw;

  _ViewModel(
      {@required this.store,
      @required this.feed,
      @required this.state,
      @required this.access,
      @required this.blurNsfw})
      : assert(store != null),
        assert(feed != null),
        assert(access != null),
        assert(blurNsfw != null);

  factory _ViewModel.build(Store<AppState> store, Feed feed) => _ViewModel(
        store: store,
        feed: feed,
        state: store.state.feeds[feed],
        access: store.state.access,
        blurNsfw: store.state.blurNsfw,
      );

  /// Whether new chunk of data is loading.
  bool get loading => state?.status == StateStatus.processing;

  /// Collection of posts.
  List<Post> get posts => state?.posts ?? const [];

  /// Real number of posts.
  int get postCount => state?.posts?.length ?? 0;

  /// Returns number of posts + virtual items.
  ///
  /// * +1 if shows access banner.
  /// * +1 if shows error or progress bar.
  int get itemCount =>
      postCount + ((hasError || loading) ? 1 : 0) + (maybeUpdateAccess ? 1 : 0);

  /// ID to get next chunk of data.
  String get next => state?.next;

  /// Whether previous load request completed with an error.
  bool get hasError => state?.exception != null;

  /// Error message if applicable.
  String get errorMessage => state?.exception?.toString() ?? '';

  bool get maybeUpdateAccess =>
      Config.supportAuthorization && !access.stable && Platform.isAndroid;

  /// Whether new chunk of data shoulddd be requested.
  ///
  /// Valid reasons:
  /// * No [FeedState] found for this [Feed].
  /// * Unseen items count is less than [loadThreshold] and not in a processing or failure state.
  bool shouldLoad(int position) {
    return state == null ||
        (position > postCount - loadThreshold && !loading && !hasError);
  }

  /// Requests new chunk of data.
  void load({bool reset = false}) {
    if (!loading) {
      store.dispatch(LoadFeedData(
        feed,
        after: reset ? null : next,
        reset: reset,
      ));
    }
  }

  /// On / Off NFSW content blur.
  void toggleBlurNsfw() =>
      store.dispatch(UpdatePreference(blurNsfw: !blurNsfw));

  /// Updates latest feed when parent Screen comes to foreground.
  void updateLatestFeed(VisibilityInfo info) {
    if (info.visibleFraction == 1) {
      store.dispatch(UpdatePreference(latestFeed: feed));
    }
  }

  /// Opens Search Screen.
  void openSearchScreen() {
    Nav.state.push(MaterialPageRoute(builder: (context) => SearchScreen()));
  }

  @override
  int get hashCode => hash([feed, state, access, blurNsfw]);

  @override
  bool operator ==(Object other) =>
      other is _ViewModel &&
      runtimeType == other.runtimeType &&
      feed == other.feed &&
      state == other.state &&
      access == other.access &&
      blurNsfw == other.blurNsfw;
}
