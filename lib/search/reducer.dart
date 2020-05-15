import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/search/actions.dart';
import 'package:starrit/search/model/state.dart';

final Reducer<AppState> searchReducer = combineReducers([
  TypedReducer<AppState, LoadSuggestions>(_loadSuggestions),
  TypedReducer<AppState, LoadSuggestionsSuccess>(_loadSuggestionsSuccess),
  TypedReducer<AppState, LoadSuggestionsFailure>(_loadSuggestionsFailure),
  TypedReducer<AppState, UpdateSort>(_updateSort),
  TypedReducer<AppState, DisposeSearchData>(_disposeSearchData),
]);

AppState _loadSuggestions(AppState state, LoadSuggestions action) =>
    state.copyWith(
      search: SearchState(
        status: StateStatus.loading,
        suggestions: state.search.suggestions,
        sort: state.search.sort,
      ),
    );

AppState _loadSuggestionsSuccess(
        AppState state, LoadSuggestionsSuccess action) =>
    state.copyWith(
      search: SearchState(
        status: StateStatus.success,
        suggestions: action.suggestions,
        sort: state.search.sort,
      ),
    );

AppState _loadSuggestionsFailure(
        AppState state, LoadSuggestionsFailure action) =>
    state.copyWith(
      search: SearchState(
        status: StateStatus.failure,
        suggestions: state.search.suggestions,
        sort: state.search.sort,
        exception: action.exception,
      ),
    );

AppState _updateSort(AppState state, UpdateSort action) => state.copyWith(
      search: SearchState(
        status: state.search.status,
        suggestions: state.search.suggestions,
        sort: action.sort,
        exception: state.search.exception,
      ),
    );

AppState _disposeSearchData(AppState state, DisposeSearchData action) =>
    state.copyWith(search: SearchState.initial);
