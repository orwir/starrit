import 'package:redux/redux.dart';
import 'package:starrit/app/reducer.dart';
import 'package:starrit/feed/reducer.dart';
import 'package:starrit/search/reducer.dart';
import 'package:starrit/settings/reducer.dart';

import 'app/state.dart';

/// Global single reducer which combines everyone else.
final Reducer<AppState> starritReducer = combineReducers([
  appReducer,
  feedReducer,
  settingsReducer,
  searchReducer,
]);
