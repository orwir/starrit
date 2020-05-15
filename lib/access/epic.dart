import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/access/actions.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/settings/actions.dart';
import 'package:starrit/settings/model/pref.dart';

final Epic<AppState> accessEpic = combineEpics([
  _saveAccess,
]);

Stream<dynamic> _saveAccess(
    Stream<dynamic> actions, EpicStore<AppState> store) {
  Future<PreferencesUpdateSuccess> save(dynamic action) async {
    final prefs = await SharedPreferences.getInstance();
    var access = Access.unspecified;
    switch (action.runtimeType) {
      case SetAnonymousAccess:
        access = Access.anonymous;
        break;
    }
    await prefs.setInt(Pref.access, access.index);
    return PreferencesUpdateSuccess();
  }

  valid(dynamic action) => action is SetAnonymousAccess;

  return actions.where(valid).switchMap((action) => save(action).asStream());
}
