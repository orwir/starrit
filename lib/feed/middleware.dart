import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/util/error.dart';
import 'package:starrit/feed/action/load.dart';
import 'package:starrit/feed/service.dart';

class FeedMiddleware extends MiddlewareClass<AppState> {
  final FeedService _service;

  FeedMiddleware(this._service) : assert(_service != null);

  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is LoadFeed) _load(store, action);
  }

  void _load(Store<AppState> store, LoadFeed action) async {
    try {
      final chunk = await _service.loadFeedChunk(
        action.feed,
        after: action.after,
      );
      store.dispatch(LoadFeedSuccess(action.feed, chunk.posts, chunk.next));
    } catch (e) {
      store.dispatch(LoadFeedFailure(action.feed, normalize(e)));
    }
  }
}
