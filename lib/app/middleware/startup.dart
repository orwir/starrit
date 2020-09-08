import 'package:redux/redux.dart';
import 'package:starrit/app/action/startup.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/network.dart';
import 'package:starrit/feed/function/feed.dart';
import 'package:starrit/settings/function/preferences.dart';

class StartupMiddleware extends MiddlewareClass<AppState> {
  final Network _network;

  StartupMiddleware(this._network) : assert(_network != null);

  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is Startup) _startup(store);
  }

  void _startup(Store<AppState> store) async {
    final init = AppState.initial;
    try {
      final prefs = await loadPreferences();
      final access = prefs.access ?? init.access;
      final blurNsfw = prefs.blurNsfw ?? init.blurNsfw;
      final feed = prefs.lastFeed ?? init.lastFeed;
      final feedData = await loadFeedChunk(_network, feed);

      store.dispatch(StartupSuccess(
        access: access,
        feed: feed,
        blurNsfw: blurNsfw,
        posts: feedData.posts,
        next: feedData.next,
      ));
    } on Exception catch (e) {
      store.dispatch(StartupFailure(e));
    }
  }
}
