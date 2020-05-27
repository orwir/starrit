import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';

/// Simple logger to track dispatched actions and state updates.
void logger(Store<AppState> store, dynamic action, NextDispatcher next) {
  next(action);
  print('> $action\n${store.state}');
}
