import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/reducer.dart';
import 'package:starrit/search/reducer.dart';
import 'package:starrit/settings/reducer.dart';
import 'package:starrit/splash/reducer.dart';

/// Global single reducer which combines everyone else.
final Reducer<AppState> starritReducer = combineReducers([
  splashReducer,
  feedReducer,
  settingsReducer,
  searchReducer,
]);
