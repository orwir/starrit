import 'package:redux/redux.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/settings/action/blur_nsfw.dart';
import 'package:starrit/settings/action/last_feed.dart';

final Reducer<AppState> settingsReducer = combineReducers([
  TypedReducer(_blurNsfw),
  TypedReducer(_lastFeed),
]);

AppState _blurNsfw(AppState state, BlurNsfw action) =>
    state.rebuild((b) => b..blurNsfw = action.enabled);

AppState _lastFeed(AppState state, LastFeed action) =>
    state.rebuild((b) => b..lastFeed = action.feed);
