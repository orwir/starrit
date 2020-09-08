import 'package:redux/redux.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/network.dart';
import 'package:starrit/feed/action/load.dart';
import 'package:starrit/feed/function/feed.dart';

class FeedMiddleware extends MiddlewareClass<AppState> {
  final Network _network;

  FeedMiddleware(this._network);

  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is LoadFeed) _load(store, action);
  }

  void _load(Store<AppState> store, LoadFeed action) async {
    try {
      final chunk = await loadFeedChunk(
        _network,
        action.feed,
        after: action.after,
      );
      store.dispatch(
        LoadFeedSuccess(action.feed, chunk.posts, chunk.next),
      );
    } on Exception catch (e) {
      store.dispatch(LoadFeedFailure(action.feed, e));
    }
  }
}
