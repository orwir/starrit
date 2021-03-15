import 'package:built_collection/built_collection.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/action/dispose.dart';
import 'package:starrit/feed/action/load.dart';
import 'package:starrit/feed/model/state.dart';

final Reducer<AppState> feedReducer = combineReducers([
  TypedReducer(_load),
  TypedReducer(_loadSuccess),
  TypedReducer(_loadFailure),
  TypedReducer(_dispose),
]);

AppState _load(AppState state, LoadFeed action) => state.rebuild((sb) => sb
  ..feeds[action.feed] = (state.feeds[action.feed] ?? FeedState()).rebuild(
    (fsb) => fsb
      ..status = Status.processing
      ..exception = null
      ..posts = action.reset ? ListBuilder() : fsb.posts
      ..next = action.reset ? null : fsb.next,
  ));

AppState _loadSuccess(AppState state, LoadFeedSuccess action) =>
    state.rebuild((sb) => sb
      ..feeds[action.feed] = state.feeds[action.feed].rebuild(
        (fsb) => fsb
          ..status = Status.success
          ..exception = null
          ..posts.addAll(action.posts)
          ..next = action.next,
      ));

AppState _loadFailure(AppState state, LoadFeedFailure action) =>
    state.rebuild((sb) => sb
      ..feeds[action.feed] = state.feeds[action.feed].rebuild(
        (fsb) => fsb
          ..status = Status.failure
          ..exception = action.exception,
      ));

AppState _dispose(AppState state, DisposeFeed action) =>
    state.rebuild((sb) => sb..feeds.remove(action.feed));
