import 'package:flutter/foundation.dart';
import 'package:redux/redux.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/access/epic.dart';
import 'package:starrit/access/reducer.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/epic.dart';
import 'package:starrit/feed/reducer.dart';
import 'package:starrit/search/epic.dart';
import 'package:starrit/search/reducer.dart';
import 'package:starrit/settings/epic.dart';
import 'package:starrit/settings/reducer.dart';

/// Every middleware should be added here.
final List<Middleware<AppState>> appMiddleware = [_appEpic, _logger];

/// Top-level reducer to rule them all.
final Reducer<AppState> appReducer = combineReducers([
  settingsReducer,
  feedReducer,
  searchReducer,
  accessReducer,
]);

final EpicMiddleware<AppState> _appEpic = EpicMiddleware(combineEpics([
  settingsEpic,
  feedEpic,
  searchEpic,
  accessEpic,
]));

void _logger(Store<AppState> store, dynamic action, NextDispatcher next) {
  next(action);
  if (kDebugMode) print('> $action\n${store.state}');
}
