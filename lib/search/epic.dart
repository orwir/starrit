import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/search/actions.dart';
import 'package:starrit/search/functions.dart';

final Epic<AppState> searchEpic = combineEpics([
  _loadSuggestions,
]);

Stream<dynamic> _loadSuggestions(
  Stream<dynamic> actions,
  EpicStore<AppState> store,
) {
  final dispose = actions.where(
      (action) => action is LoadSuggestions || action is DisposeSearchData);

  Stream<dynamic> invoke(LoadSuggestions request) =>
      loadSuggestions(request, store.state.access)
          .then((value) => LoadSuggestionsSuccess(value))
          .catchError((error) => LoadSuggestionsFailure(error))
          .asStream()
          .takeUntil(dispose);

  return actions
      .whereType<LoadSuggestions>()
      .debounceTime(Duration(milliseconds: 250))
      .switchMap(invoke);
}
