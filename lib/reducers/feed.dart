import 'package:starrit/actions/feed.dart';
import 'package:starrit/models/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is StartLoadingPostsAction) {
    return state.copyWith(
      feed: action.feed,
      posts: state.feed == action.feed ? state.posts : const [],
      loading: true,
      exception: null,
    );
  }

  if (action is LoadingPostsSuccessAction) {
    return state.copyWith(
      feed: action.feed,
      loading: false,
      posts: [...?state.posts, ...action.posts],
      exception: null,
    );
  }

  if (action is LoadingPostsFailureAction) {
    return state.copyWith(
      feed: action.feed,
      loading: false,
      exception: action.exception,
    );
  }

  if (action is ChangeBlurNsfwAction) {
    return state.copyWith(
      blurNsfw: action.blurNsfw,
    );
  }

  return state;
}
