import 'package:redux/redux.dart';
import 'package:starrit/app/action/startup.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/state.dart';

/// Collector of all app-level reducers.
final Reducer<AppState> appReducer = combineReducers([
  TypedReducer(_startup),
  TypedReducer(_startupSuccess),
  TypedReducer(_startupFailure),
]);

AppState _startup(AppState state, Startup action) => AppState.initial;

AppState _startupSuccess(AppState state, StartupSuccess action) =>
    AppState.initial.rebuild((b) => b
      ..status = Status.success
      ..access = action.access
      ..lastFeed = action.feed
      ..blurNsfw = action.blurNsfw
      ..feeds[action.feed] = FeedState(
        (fb) => fb
          ..status = Status.success
          ..posts.addAll(action.posts)
          ..next = action.next,
      ));

AppState _startupFailure(AppState state, StartupFailure action) =>
    AppState.initial.rebuild((b) => b
      ..status = Status.failure
      ..exception = action.exception);
