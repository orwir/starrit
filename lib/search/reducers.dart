import 'package:starrit/common/models/state.dart';

import 'actions.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is SearchDisposeAction) {
    return state.copyWith(search: SearchState.none);
  }
  if (action is SearchSortChangeAction) {
    return state.copyWith(search: state.search.copyWith(sort: action.sort));
  }
  if (action is SearchSuggestionsResponseAction) {
    return state.copyWith(
      search: state.search.copyWith(
        query: action.query,
        suggestions: action.suggestions,
      ),
    );
  }
  return state;
}
