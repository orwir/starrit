import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/settings/actions.dart';

final Reducer<AppState> settingsReducer = combineReducers([
  TypedReducer<AppState, UpdateBlurNsfw>(_upddateBlurNsfw),
  TypedReducer<AppState, UpdateLatestFeed>(_updateLatestFeed),
]);

AppState _upddateBlurNsfw(AppState state, UpdateBlurNsfw action) =>
    state.copyWith(blurNsfw: action.blurNsfw);

AppState _updateLatestFeed(AppState state, UpdateLatestFeed action) =>
    state.copyWith(latestFeed: action.feed);
