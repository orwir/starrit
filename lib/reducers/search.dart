import 'package:starrit/actions/search.dart';
import 'package:starrit/models/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is SearchDisposeAction) {
    return state.copyWith(search: SearchState.initial());
  }
  if (action is SortUpdateAction) {
    return state.copyWith(search: state.search.copyWith(sort: action.sort));
  }
  return state;
}
