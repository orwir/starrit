import 'package:redux/redux.dart';
import 'package:starrit/common/service.dart';
import 'package:starrit/splash/action/startup.dart';
import 'package:starrit/common/model/state.dart';

class SplashMiddleware extends MiddlewareClass<AppState> {
  final StarritService _service;

  SplashMiddleware(this._service);

  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is Startup) _startup(store);
  }

  void _startup(Store<AppState> store) async {
    final init = AppState.initial;
    try {
      final prefs = await _service.loadPreferences();
      final access = prefs.access ?? init.access;
      final blurNsfw = prefs.blurNsfw ?? init.blurNsfw;
      final feed = prefs.lastFeed ?? init.lastFeed;
      final chunk = await _service.loadFeedChunk(feed);

      store.dispatch(StartupSuccess(
        access: access,
        blurNsfw: blurNsfw,
        feed: feed,
        posts: chunk.posts,
        next: chunk.next,
      ));
    } catch (e) {
      store.dispatch(StartupFailure(e));
    }
  }
}
