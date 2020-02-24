import 'package:redux/redux.dart';
import 'package:starrit/models/state.dart';

void logger(Store<AppState> store, dynamic action, NextDispatcher next) {
  next(action);
  print('action=$action, state=${store.state}');
}
