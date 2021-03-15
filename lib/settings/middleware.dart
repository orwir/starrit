import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/action/blur_nsfw.dart';
import 'package:starrit/settings/action/last_feed.dart';
import 'package:starrit/settings/service.dart';

class SettingsMiddleware extends MiddlewareClass<AppState> {
  final SettingsService _service;

  SettingsMiddleware(this._service) : assert(_service != null);

  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is BlurNsfw) _blurNsfw(action.enabled);
    if (action is LastFeed) _lastFeed(action.feed);
  }

  void _blurNsfw(bool enabled) async {
    try {
      await _service.updateBlurNsfw(enabled);
    } catch (e) {
      // TODO: implement
    }
  }

  void _lastFeed(Feed feed) async {
    try {
      await _service.updateLastFeed(feed);
    } catch (e) {
      // TODO: implement
    }
  }
}
