import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/common/model/optional.dart';
import 'package:starrit/settings/actions.dart';

final Reducer<AppState> settingsReducer = combineReducers([
  TypedReducer<AppState, UpdatePreferenceSuccess>(_updatePreferenceSuccess),
  TypedReducer<AppState, UpdatePreferenceFailure>(_updatePreferenceFailure),
]);

AppState _updatePreferenceSuccess(
        AppState state, UpdatePreferenceSuccess action) =>
    state.copyWith(
      latestFeed: action.latestFeed ?? state.latestFeed,
      blurNsfw: action.blurNsfw ?? state.blurNsfw,
    );

AppState _updatePreferenceFailure(
        AppState state, UpdatePreferenceFailure action) =>
    state.copyWith(
      status: StateStatus.failure,
      exception: action.exception.optional,
    );
