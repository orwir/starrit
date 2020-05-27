import 'package:redux/redux.dart';
import 'package:redux_thunk/redux_thunk.dart';
import 'package:starrit/access/actions.dart';
import 'package:starrit/access/functions.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/settings/functions.dart';

ThunkAction<AppState> setAnonymousAccess() {
  return (Store<AppState> store) async {
    await saveAccess(Access.anonymous);
    store.dispatch(SetAnonymousAccess());
  };
}

ThunkAction<AppState> startAuthorization(String requestState) {
  return (Store<AppState> store) async {
    await openAuthorizationService(requestState);
    //store.dispatch(action); // TODO:
  };
}
