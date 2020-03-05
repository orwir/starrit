import 'package:redux/redux.dart';
import 'package:starrit/models/state.dart';

import 'feed.dart' as feed;
import 'preference.dart' as preference;
import 'search.dart' as search;

Reducer<AppState> get reducer => combineReducers([
      feed.reducer,
      preference.reducer,
      search.reducer,
    ]);
