import 'package:starrit/actions/feed.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/models/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is PostsLoadingStartAction) {
    return state.copyWith(
      feedState: FeedState.loading(
        feed: action.feed,
        posts: state.feedState?.posts ?? const <Post>[],
      ),
    );
  }

  if (action is PostsLoadingSuccessAction) {
    return state.copyWith(
      feedState: FeedState.loaded(
        feed: action.feed,
        posts: [...?state.feedState?.posts, ...action.posts],
      ),
    );
  }

  if (action is PostsLoadingFailureAction) {
    return state.copyWith(
      feedState: FeedState.failed(
        feed: action.feed,
        posts: state.feedState?.posts ?? const <Post>[],
        error: action.error,
      ),
    );
  }

  return state;
}
