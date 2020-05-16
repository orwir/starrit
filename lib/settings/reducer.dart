import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/settings/actions.dart';

final Reducer<AppState> settingsReducer = combineReducers([
  TypedReducer<AppState, LoadPreferences>(_loadPreferences),
  TypedReducer<AppState, LoadPreferencesSuccess>(_loadPreferencesSuccess),
  TypedReducer<AppState, LoadPreferencesFailure>(_loadPreferencesFailure),
  TypedReducer<AppState, UpdateLatestFeed>(_updateLatestFeed),
  TypedReducer<AppState, UpdateBlurNsfw>(_updateBlurNsfw),
]);

AppState _loadPreferences(AppState state, LoadPreferences actions) =>
    state.copyWith(status: StateStatus.loading);

AppState _loadPreferencesSuccess(
        AppState state, LoadPreferencesSuccess action) =>
    state.copyWith(
      status: StateStatus.success,
      access: action.access,
      latestFeed: action.latestFeed,
      blurNsfw: action.blurNsfw,
    );

AppState _loadPreferencesFailure(
        AppState state, LoadPreferencesFailure action) =>
    state.copyWith(status: StateStatus.failure);

AppState _updateLatestFeed(AppState state, UpdateLatestFeed action) =>
    state.copyWith(latestFeed: action.feed);

AppState _updateBlurNsfw(AppState state, UpdateBlurNsfw action) =>
    state.copyWith(blurNsfw: action.blurNsfw);
