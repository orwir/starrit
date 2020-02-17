import 'package:redux/redux.dart';
import 'package:starrit/action/feed.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/state.dart';
import 'package:starrit/repository/feed.dart';

List<Middleware<AppState>> createFeedMiddlewares(FeedRepository repository) {
  return [
    _LoadLatestFeed(repository),
    _LoadFeedData(repository),
  ];
}

class _LoadLatestFeed implements MiddlewareClass<AppState> {
  final FeedRepository repository;

  _LoadLatestFeed(this.repository);

  @override
  call(Store<AppState> store, dynamic action, NextDispatcher next) {
    if (action is LoadLatestFeedAction) {
      fetchLatestFeed(store);
    }
    next(action);
  }

  void fetchLatestFeed(Store<AppState> store) async {
    var feed = await repository.latestFeed();
    store.dispatch(LoadFeedDataAction(feed));
  }
}

class _LoadFeedData implements MiddlewareClass<AppState> {
  final FeedRepository repository;

  _LoadFeedData(this.repository);

  @override
  call(Store<AppState> store, action, next) {
    if (action is LoadFeedDataAction) {
      fetchFeedData(store, action.feed);
    }
    next(action);
  }

  void fetchFeedData(Store<AppState> store, Feed feed) async {
    try {
      final posts = await repository.fetchFeedData(feed);
      store.dispatch(FeedDataLoadSucceededAction(feed, posts));
    } catch (exception) {
      store.dispatch(FeedDataLoadFailedAction(feed, exception));
    }
  }
}
