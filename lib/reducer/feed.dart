import 'package:redux/redux.dart';
import 'package:starrit/model/state.dart';

List<Reducer<AppState>> createFeedReducers() {
  return <Reducer<AppState>>[];
}




/*
AppState reducer(AppState state, dynamic action) {
  if (action is LoadFeedDataAction) {
    // TODO: show progress bar
  }
  if (action is FeedDataLoadSucceededAction) {}
  if (action is FeedDataLoadFailedAction) {
    // TODO: show that something went wrong
  }
  return state;
}



AppState _onFeedDataLoadSucceeded(
  AppState state,
  FeedDataLoadSucceededAction action,
) {
  var posts = action.posts;
  if (action.feed == state.feed) {
    posts = [...?state.posts, ...action.posts];
  }
  return AppState(
    feed: action.feed,
    posts: posts,
  );
}
*/
