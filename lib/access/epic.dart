import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/access/actions.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/model/duration.dart';
import 'package:starrit/access/model/scope.dart';
import 'package:starrit/common/config.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/settings/model/pref.dart';
import 'package:url_launcher/url_launcher.dart';

final Epic<AppState> accessEpic = combineEpics([
  TypedEpic<AppState, SetAnonymousAccess>(_setAnonymousAccess),
  TypedEpic<AppState, StartAuthorization>(_startAuthorization),
]);

Stream<dynamic> _setAnonymousAccess(
  Stream<SetAnonymousAccess> actions,
  EpicStore<AppState> store,
) {
  Future<AccessUpdateSuccess> save(SetAnonymousAccess action) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt(Pref.access, Access.anonymous.index);
    return AccessUpdateSuccess();
  }

  return actions.switchMap((action) => save(action).asStream());
}

Stream<dynamic> _startAuthorization(
  Stream<StartAuthorization> actions,
  EpicStore<AppState> store,
) {
  Future<AccessUpdateSuccess> start(StartAuthorization action) async {
    final scope =
        [Scope.identity, Scope.read].map((s) => s.parameter).join(',');

    final requestUri = 'https://www.reddit.com/api/v1/authorize.compact'
        '?client_id=${Config.clientID}'
        '&response_type=code'
        '&state=${action.state}'
        '&redirect_uri=starrit://authorization'
        '&duration=${TokenDuration.permanent.parameter}'
        '&scope=$scope';

    if (await canLaunch(requestUri)) {
      await launch(requestUri);
    }

    return AccessUpdateSuccess();
  }

  return actions.switchMap((action) => start(action).asStream());
}
