import 'package:flutter/foundation.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';

/// Simple logger to track dispatched actions and state updates (debug only).
void logger(Store<AppState> store, dynamic action, NextDispatcher next) {
  next(action);
  if (kDebugMode) print('> $action\n${store.state}');
}

void handleLink(String link) {
  print(link); // TODO: implement
}
