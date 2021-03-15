import 'package:redux/redux.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/splash/action/startup.dart';
import 'package:starrit/common/model/state.dart';

final Reducer<AppState> splashReducer = combineReducers([
  TypedReducer(_startup),
  TypedReducer(_startupSuccess),
  TypedReducer(_startupFailure),
]);

AppState _startup(AppState state, Startup action) => AppState.initial;

AppState _startupSuccess(AppState state, StartupSuccess action) =>
    state.rebuild((b) => b
      ..status = Status.success
      ..exception = null
      ..access = action.access
      ..lastFeed = action.feed
      ..blurNsfw = action.blurNsfw
      ..feeds[action.feed] = FeedState((fb) => fb
        ..status = Status.success
        ..posts.addAll(action.posts)
        ..next = action.next));

AppState _startupFailure(AppState state, StartupFailure action) =>
    state.rebuild((b) => b
      ..status = Status.failure
      ..exception = action.exception);
