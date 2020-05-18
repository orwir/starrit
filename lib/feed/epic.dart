import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/functions.dart';

final Epic<AppState> feedEpic = combineEpics([
  _loadData,
]);

Stream<dynamic> _loadData(Stream<dynamic> actions, EpicStore<AppState> store) {
  Stream<dynamic> dispose(LoadFeedData request) => actions.where(
        (action) =>
            (action is LoadFeedData && action.feed == request.feed) ||
            (action is DisposeFeedData && action.feed == request.feed),
      );

  Stream<dynamic> invoke(LoadFeedData request) =>
      loadFeedData(request, store.state.access)
          .asStream()
          .takeUntil(dispose(request));

  return actions.whereType<LoadFeedData>().switchMap(invoke);
}
