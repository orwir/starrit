import 'package:redux/redux.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/settings/action/blur_nsfw.dart';
import 'package:starrit/settings/action/last_feed.dart';
import 'package:starrit/settings/function/preferences.dart';

class SettingsMiddleware extends MiddlewareClass<AppState> {
  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is BlurNsfw) updateBlurNsfw(action.enabled);
    if (action is LastFeed) updateLastFeed(action.feed);
  }
}
