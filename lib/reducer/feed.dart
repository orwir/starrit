import 'package:starrit/action/feed.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/model/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is LoadPostsAction) {
    return state.copyWith(
      feedState: FeedState.loading(
        feed: action.feed,
        posts: state.feedState?.posts ?? const <Post>[],
      ),
    );
  }

  if (action is LoadPostsSuccessAction) {
    return state.copyWith(
      feedState: FeedState.loaded(
        feed: action.feed,
        posts: action.posts,
      ),
    );
  }

  if (action is LoadPostsFailureAction) {
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
