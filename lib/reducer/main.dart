import 'package:redux/redux.dart';
import 'package:starrit/model/state.dart';
import 'package:starrit/reducer/feed.dart' as feed;

Reducer<AppState> get reducer => combineReducers([
      feed.reducer,
    ]);
