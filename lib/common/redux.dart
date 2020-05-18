import 'package:redux/redux.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/access/epic.dart';
import 'package:starrit/access/reducer.dart';
import 'package:starrit/splash/epic.dart';
import 'package:starrit/common/functions.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/splash/reducer.dart';
import 'package:starrit/feed/epic.dart';
import 'package:starrit/feed/reducer.dart';
import 'package:starrit/search/epic.dart';
import 'package:starrit/search/reducer.dart';
import 'package:starrit/settings/epic.dart';
import 'package:starrit/settings/reducer.dart';

/// Middlewares collector.
final List<Middleware<AppState>> appMiddleware = [_appEpic, logger];

/// Reders collector.
final Reducer<AppState> appReducer = combineReducers([
  splashReducer,
  settingsReducer,
  feedReducer,
  searchReducer,
  accessReducer,
]);

/// Epics collector.
final EpicMiddleware<AppState> _appEpic = EpicMiddleware(combineEpics([
  splashEpic,
  settingsEpic,
  feedEpic,
  searchEpic,
  accessEpic,
]));
