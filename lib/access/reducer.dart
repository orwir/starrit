import 'package:redux/redux.dart';
import 'package:starrit/access/actions.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/model/state.dart';
import 'package:starrit/common/model/optional.dart';
import 'package:starrit/common/model/state.dart';

final Reducer<AppState> accessReducer = combineReducers([
  TypedReducer<AppState, SetAnonymousAccess>(_setAnonymousAccess),
  TypedReducer<AppState, StartAuthorization>(_startAuthorization),
]);

AppState _setAnonymousAccess(AppState state, SetAnonymousAccess action) =>
    state.copyWith(access: Access.anonymous, auth: Optional(null));

AppState _startAuthorization(AppState state, StartAuthorization action) =>
    state.copyWith(
      access: Access.unspecified,
      auth: AuthState().optional, // TODO:
    );
