import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/actions.dart';
import 'package:starrit/common/functions.dart';
import 'package:starrit/common/model/state.dart';

final Epic<AppState> commonEpic = combineEpics([
  TypedEpic<AppState, InitApplication>(_initApplication),
]);

Stream<dynamic> _initApplication(
        Stream<InitApplication> actions, EpicStore<AppState> store) =>
    actions.switchMap(
      (action) => initApplication(store.state).asStream().takeUntil(actions),
    );
