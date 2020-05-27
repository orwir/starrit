import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/splash/actions.dart';
import 'package:starrit/splash/functions.dart';

final splashEpics = [
  TypedEpic<AppState, InitApplication>(_initApplication),
];

Stream<dynamic> _initApplication(
  Stream<InitApplication> actions,
  EpicStore<AppState> store,
) =>
    actions.asyncMap((_) => loadInitialState()
        .then((value) => InitApplicationSuccess(value))
        .catchError((error) => InitApplicationFailure(error)));
