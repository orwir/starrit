import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/settings/actions.dart';
import 'package:starrit/settings/functions.dart';

final Epic<AppState> settingsEpic = combineEpics([
  TypedEpic<AppState, UpdatePreference>(_updatePreference),
]);

Stream<dynamic> _updatePreference(
        Stream<UpdatePreference> actions, EpicStore<AppState> store) =>
    actions.asyncMap(updatePreference);
