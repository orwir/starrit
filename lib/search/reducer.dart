import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/search/action/dispose.dart';
import 'package:starrit/search/action/load.dart';
import 'package:starrit/search/action/sort.dart';

final Reducer<AppState> searchReducer = combineReducers([
  TypedReducer(_load),
  TypedReducer(_loadSuccess),
  TypedReducer(_loadFailure),
  TypedReducer(_updateSort),
  TypedReducer(_dispose),
]);

AppState _load(AppState state, LoadSuggestions action) => state.rebuild((b) => b
  ..status = Status.processing
  ..exception = null);

AppState _loadSuccess(AppState state, LoadSuggestionsSuccess action) =>
    state.rebuild((b) => b
      ..status = Status.success
      ..exception = null
      ..searchSuggestions.replace(action.suggestions));

AppState _loadFailure(AppState state, LoadSuggestionsFailure action) =>
    state.rebuild((b) => b
      ..status = Status.failure
      ..exception = action.exception);

AppState _updateSort(AppState state, UpdateSort action) =>
    state.rebuild((b) => b..searchSort = action.sort);

AppState _dispose(AppState state, DisposeSearch action) =>
    state.rebuild((b) => b
      ..status = Status.success
      ..exception = null
      ..searchSuggestions.replace(FeedType.values)
      ..searchSort = FeedSort.best);
