import 'package:redux/redux.dart';
import 'package:starrit/action/main.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/model/state.dart';

void fetchLatestFeed(
  Store<AppState> store,
  dynamic action,
  NextDispatcher next,
) {
  if (action is LoadLatestFeedAction) {
    Future.delayed(Duration(seconds: 2), () {
      final feed = Feed.home();
      final posts = <Post>[];
      store.dispatch(FeedDataLoadedAction(feed, posts));
    });
  }
  next(action);
}
