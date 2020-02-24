import 'package:redux/redux.dart';
import 'package:starrit/models/state.dart';

import 'feed.dart' as feed;

Reducer<AppState> get reducer => combineReducers([
      feed.reducer,
    ]);
