import 'package:starrit/action/main.dart';
import 'package:starrit/model/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is FeedDataLoadedAction) {
    var posts = action.posts;
    if (action.feed == state.feed) {
      posts = [...?state.posts, ...action.posts];
    }
    return AppState(
      feed: action.feed,
      posts: posts,
    );
  }
  return state;
}
