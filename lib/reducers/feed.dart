import 'package:starrit/actions/feed.dart';
import 'package:starrit/models/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is FeedRequestAction) {
    return state.copyWith(
      feeds: {
        ...state.feeds,
        action.feed: FeedState(
          feed: action.feed,
          loading: true,
          exception: null,
          posts: [...?state[action.feed]?.posts],
        ),
      },
    );
  }

  if (action is FeedDisposeAction) {
    return state.copyWith(
      feeds: Map.fromEntries(
        state.feeds.entries.where((entry) => entry.key != action.feed),
      ),
    );
  }

  if (action is FeedResponseSuccessAction) {
    return state.copyWith(
      feeds: {
        ...state.feeds,
        action.feed: FeedState(
          feed: action.feed,
          loading: false,
          exception: null,
          posts: [...?state[action.feed]?.posts, ...action.posts],
        ),
      },
    );
  }

  if (action is FeedResponseFailureAction) {
    return state.copyWith(
      feeds: {
        ...state.feeds,
        action.feed: FeedState(
          feed: action.feed,
          loading: false,
          exception: action.exception,
          posts: state[action.feed].posts,
        ),
      },
    );
  }

  return state;
}
