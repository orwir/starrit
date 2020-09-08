import 'package:built_collection/built_collection.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/widget/banner.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/env.dart';
import 'package:starrit/feed/action/dispose.dart';
import 'package:starrit/feed/action/load.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:starrit/feed/state.dart';
import 'package:starrit/feed/widget/post/post.dart';
import 'package:starrit/search/screen.dart';
import 'package:starrit/settings/action/blur_nsfw.dart';
import 'package:starrit/settings/action/last_feed.dart';
import 'package:visibility_detector/visibility_detector.dart';

/// Feed screen.
class FeedScreen extends StatelessWidget {
  /// Specific feed to display.
  final Feed feed;

  final _scrollController = ScrollController();

  FeedScreen(this.feed) : assert(feed != null);

  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        onDispose: (store) async {
          _scrollController.dispose();
          store.dispatch(DisposeFeed(feed));
        },
        onInitialBuild: (viewModel) {
          if (viewModel.shouldLoad(0)) viewModel.load();
        },
        distinct: true,
        converter: (store) => _ViewModel(store, feed),
        builder: (context, viewModel) => VisibilityDetector(
          key: ObjectKey(feed),
          onVisibilityChanged: viewModel.updateLastFeed,
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
                  onPressed: () => viewModel.openSearchScreen(context),
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
    if (viewModel.failed) {
      return _FailureView(viewModel);
    }
    return LinearProgressIndicator();
  }
}

class _FailureView extends StatelessWidget {
  final _ViewModel viewModel;

  _FailureView(this.viewModel);

  @override
  Widget build(BuildContext context) => Container(
        padding: EdgeInsets.symmetric(horizontal: 16),
        child: Row(
          children: [
            Expanded(
              child: Text(
                viewModel.errorMessage,
                overflow: TextOverflow.ellipsis,
              ),
            ),
            FlatButton(
              onPressed: viewModel.load,
              textTheme: ButtonTextTheme.primary,
              child: Text('RETRY'),
            ),
          ],
        ),
      );
}

@immutable
class _ViewModel {
  /// Determines how many items may left unseen before request new chunk.
  static const loadThreshold = 10;

  final Store<AppState> store;

  /// Specific feed to handle.
  final Feed feed;

  /// State for the [feed].
  final FeedState state;

  /// User's access status.
  final Access access;

  /// If true NSFW-content will be blurred by default.
  final bool blurNsfw;

  _ViewModel(this.store, this.feed)
      : assert(store != null),
        assert(store.state != null),
        assert(feed != null),
        state = store.state.feeds[feed],
        access = store.state.access,
        blurNsfw = store.state.blurNsfw;

  /// Whether data processing is in progress.
  bool get processing => state?.status == Status.processing;

  /// Whether data processing completed with an error.
  bool get failed => state?.status == Status.failure;

  /// Returns error message if applicable.
  ///
  /// For debug mode returns exact message. For prod - placeholder.
  String get errorMessage => kDebugMode
      ? state.exception.toString()
      : 'Request failed. Please try again.';

  /// Loaded posts.
  BuiltList<Post> get posts => state?.posts ?? BuiltList();

  /// Number of loaded posts.
  int get postCount => posts.length;

  /// Returns number of posts + virtual items.
  ///
  /// * +1 if shows access banner.
  /// * +1 if shows error or progress bar.
  int get itemCount =>
      postCount +
      ((failed || processing) ? 1 : 0) +
      (maybeUpdateAccess ? 1 : 0);

  /// ID to get next chunk of data.
  String get next => state?.next;

  /// True if app build supports authorization and user hasn't choose access type yet.
  bool get maybeUpdateAccess => Env.supportAuthorization && !access.stable;

  /// Whether new chunk of data shoulddd be requested.
  ///
  /// Valid reasons:
  /// * No [FeedState] found for this [Feed].
  /// * Unseen items count is less than [loadThreshold] and not in a processing or failure state.
  bool shouldLoad(int position) {
    return state == null ||
        (position > postCount - loadThreshold &&
            !processing &&
            !failed &&
            next != null);
  }

  /// Requests new chunk of data.
  void load({bool reset = false}) {
    if (!processing) {
      store.dispatch(LoadFeed(
        feed,
        after: reset ? null : next,
        reset: reset,
      ));
    }
  }

  /// On / Off NFSW content blur.
  void toggleBlurNsfw() {
    store.dispatch(BlurNsfw(!blurNsfw));
  }

  /// Opens Search Screen.
  void openSearchScreen(BuildContext context) {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => SearchScreen()),
    );
  }

  void updateLastFeed(VisibilityInfo info) {
    if (info.visibleFraction == 1) {
      store.dispatch(LastFeed(feed));
    }
  }

  @override
  int get hashCode => hash([feed, state, access, blurNsfw]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is _ViewModel &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          state == other.state &&
          access == other.access &&
          blurNsfw == other.blurNsfw;
}
