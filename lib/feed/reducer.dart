import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/model/state.dart';

final Reducer<AppState> feedReducer = combineReducers([
  TypedReducer<AppState, LoadFeedData>(_loadFeedData),
  TypedReducer<AppState, LoadFeedDataSuccess>(_loadFeedDataSuccess),
  TypedReducer<AppState, LoadFeedDataFailure>(_loadFeedDataFailure),
  TypedReducer<AppState, DisposeFeedData>(_disposeFeedData),
]);

AppState _loadFeedData(AppState state, LoadFeedData action) {
  return state.copyWith(feeds: {
    ...state.feeds,
    action.feed: state.feeds[action.feed]?.toLoading(reset: action.reset) ??
        FeedState(
          feed: action.feed,
          status: StateStatus.loading,
          posts: const [],
        ),
  });
}

AppState _loadFeedDataSuccess(AppState state, LoadFeedDataSuccess action) {
  return state.copyWith(feeds: {
    ...state.feeds,
    action.feed:
        state.feeds[action.feed]?.toSuccess(action.posts, action.next) ??
            FeedState(
              feed: action.feed,
              status: StateStatus.success,
              posts: action.posts,
              next: action.next,
            ),
  });
}

AppState _loadFeedDataFailure(AppState state, LoadFeedDataFailure action) {
  return state.copyWith(feeds: {
    ...state.feeds,
    action.feed: state.feeds[action.feed]?.toFailure(action.exception) ??
        FeedState(
          feed: action.feed,
          status: StateStatus.failure,
          posts: const [],
          exception: action.exception,
        ),
  });
}

AppState _disposeFeedData(AppState state, DisposeFeedData action) =>
    state.copyWith(feeds: {
      for (final feedState in state.feeds.values)
        if (feedState.feed != action.feed) feedState.feed: feedState
    });
