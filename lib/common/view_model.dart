import 'package:flutter/foundation.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';

@immutable
abstract class ViewModel {
  final Store<AppState> store;
  final AppState state;

  ViewModel(Store<AppState> store)
      : assert(store != null),
        assert(store.state != null),
        this.store = store,
        this.state = store.state;

  bool get processing => state.status == Status.processing;

  bool get success => state.status == Status.success;

  bool get failure => state.status == Status.failure;
}
