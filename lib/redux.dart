import 'package:redux/redux.dart';
import 'package:redux_epics/redux_epics.dart';

import 'models/state.dart';
import 'feed/reducers.dart' as feed;
import 'feed/epics.dart' as feed;
import 'preferences/reducers.dart' as preference;
import 'search/reducers.dart' as search;
import 'search/epics.dart' as search;

final Reducer<AppState> reducer = combineReducers([
  feed.reducer,
  preference.reducer,
  search.reducer,
]);

final middleware = <Middleware<AppState>>[_epicMiddleware, _logger];

final _epicMiddleware = EpicMiddleware(combineEpics<AppState>([
  feed.epic,
  search.epic,
]));

void _logger(Store<AppState> store, dynamic action, NextDispatcher next) {
  next(action);
  print('action:$action, state:${store.state}');
}
