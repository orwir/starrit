import 'package:redux/redux.dart';
import 'package:starrit/common/model/optional.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/splash/actions.dart';

final Reducer<AppState> splashReducer = combineReducers([
  TypedReducer<AppState, InitApplicationSuccess>(_initApplicationSuccess),
  TypedReducer<AppState, InitApplicationFailure>(_initApplicationFailure),
]);

AppState _initApplicationSuccess(
        AppState state, InitApplicationSuccess action) =>
    state.copyWith(
      status: StateStatus.success,
      access: action.access,
      auth: action.auth?.optional,
      blurNsfw: action.blurNsfw,
      latestFeed: action.feed,
      feeds: {
        ...state.feeds,
        action.feed: action.feedState,
      },
      exception: Optional(null),
    );

AppState _initApplicationFailure(
        AppState state, InitApplicationFailure action) =>
    state.copyWith(
      status: StateStatus.failure,
      exception: action.exception.optional,
    );
