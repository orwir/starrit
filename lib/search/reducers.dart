import 'package:starrit/models/state.dart';

import 'actions.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is SearchDisposeAction) {
    return state.copyWith(search: SearchState.initial);
  }
  if (action is SortUpdateAction) {
    return state.copyWith(search: state.search.copyWith(sort: action.sort));
  }
  return state;
}
