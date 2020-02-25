import 'package:starrit/actions/feed.dart';
import 'package:starrit/models/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is StartLoadingPostsAction) {
    return state.copyWith(
      loading: true,
      exception: null,
    );
  }

  if (action is LoadingPostsSuccessAction) {
    return state.copyWith(
      loading: false,
      posts: [...?state.posts, ...action.posts],
      exception: null,
    );
  }

  if (action is LoadingPostsFailureAction) {
    return state.copyWith(
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
