import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/access/actions.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/settings/model/pref.dart';

final Epic<AppState> accessEpic = combineEpics([
  TypedEpic<AppState, SetAnonymousAccess>(_setAnonymousAccess),
]);

Stream<dynamic> _setAnonymousAccess(
  Stream<SetAnonymousAccess> actions,
  EpicStore<AppState> store,
) {
  Future<AccessUpdateSuccess> save(dynamic action) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt(Pref.access, Access.anonymous.index);
    return AccessUpdateSuccess();
  }

  return actions.switchMap((action) => save(action).asStream());
}
