import 'package:flutter/foundation.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';

class LoggerMiddleware extends MiddlewareClass<AppState> {
  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (kDebugMode) print('> $action\n${store.state}');
  }
}
