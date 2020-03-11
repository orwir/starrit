import 'package:redux/redux.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/feed/reducers.dart' as feed;
import 'package:starrit/feed/epics.dart' as feed;
import 'package:starrit/preferences/reducers.dart' as preference;
import 'package:starrit/search/reducers.dart' as search;
import 'package:starrit/search/epics.dart' as search;

import 'models/state.dart';

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
