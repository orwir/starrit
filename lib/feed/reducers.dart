import 'package:redux/redux.dart';
import 'package:starrit/models/state.dart';

import 'actions.dart';

final reducer = combineReducers<AppState>([
  TypedReducer<AppState, FeedRequestAction>(_requestFeed),
  TypedReducer<AppState, FeedResponseSuccessAction>(_feedRequestSuccess),
  TypedReducer<AppState, FeedResponseFailureAction>(_feedRequestFailure),
  TypedReducer<AppState, FeedDisposeAction>(_disposeFeed),
]);

AppState _requestFeed(AppState state, FeedRequestAction action) {
  return state.copyWith(feeds: {
    ...state.feeds,
    action.feed: state[action.feed]?.toLoading(reset: action.reset) ??
        FeedState(
          feed: action.feed,
          loading: true,
          posts: const [],
        ),
  });
}

AppState _feedRequestSuccess(AppState state, FeedResponseSuccessAction action) {
  return state.copyWith(feeds: {
    ...state.feeds,
    action.feed: state[action.feed]?.toSuccess(action.posts, action.next) ??
        FeedState(
          feed: action.feed,
          loading: false,
          posts: action.posts,
          next: action.next,
        ),
  });
}

AppState _feedRequestFailure(AppState state, FeedResponseFailureAction action) {
  return state.copyWith(feeds: {
    ...state.feeds,
    action.feed: state[action.feed]?.toFailure(action.exception) ??
        FeedState(
          feed: action.feed,
          loading: false,
          exception: action.exception,
          posts: const [],
        ),
  });
}

AppState _disposeFeed(AppState state, FeedDisposeAction action) {
  return state.copyWith(
    feeds: Map.fromEntries(
        state.feeds.entries.where((entry) => entry.key != action.feed)),
  );
}
