import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
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

/// Displays feed data.
class FeedScreen extends StatelessWidget {
  /// Specific feed to show data for.
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
  /// Determines how many items left unseen before request new chunk.
  static const loadThreshold = 10;

  /// Redux-store.
  final Store<AppState> store;

  /// Feed metadata.
  final Feed feed;

  /// Current feed state.
  /// Might be null.
  final FeedState state;

  _ViewModel(this.store, this.feed, this.state)
      : assert(store != null),
        assert(feed != null);

  factory _ViewModel.build(Store<AppState> store, Feed feed) =>
      _ViewModel(store, feed, store.state.feeds[feed]);

  /// Whether data is loading.
  bool get loading => state?.status == StateStatus.loading;
  bool get blurNsfw => store.state.blurNsfw;

  /// Feed posts.
  List<Post> get posts => state?.posts ?? const [];

  /// Total number of items loaded.
  int get postCount => state?.posts?.length ?? 0;

  /// Total number of items loaded + virtual item if has error.
  int get itemCount => postCount + ((hasError || loading) ? 1 : 0);

  /// ID to get next chunk of data.
  String get next => state?.next;

  /// Whether previous load request completed with an error.
  bool get hasError => state?.exception != null;

  /// Error message if applicable.
  String get errorMessage => state?.exception?.toString() ?? '';

  /// Determines is new chunk of data should be requested.
  /// True if:
  /// * no [FeedState] found
  /// * Unseen items count is less than [loadThreshold] and not in a loading or failure state.
  bool shouldLoad(int position) {
    return state == null ||
        (position > postCount - loadThreshold && !loading && !hasError);
  }

  /// Request new chunk of data.
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
  void toggleBlurNsfw() => store.dispatch(UpdateBlurNsfw(!blurNsfw));

  void updateLatestFeed(VisibilityInfo info) {
    if (info.visibleFraction == 1) {
      store.dispatch(UpdateLatestFeed(feed));
    }
  }

  /// Opens Search Screen.
  void openSearchScreen() {
    navigatorStore.currentState
        .push(MaterialPageRoute(builder: (context) => SearchScreen()));
  }

  @override
  int get hashCode => hash([feed, state]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is _ViewModel &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          state == other.state;
}
