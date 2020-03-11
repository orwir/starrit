import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/models/state.dart';
import 'package:starrit/feed/models/feed.dart';

import 'actions.dart';

final epic = combineEpics<AppState>([
  TypedEpic<AppState, SearchSuggestionsRequestAction>(_requestSuggestions),
]);

Stream<dynamic> _requestSuggestions(
  Stream<SearchSuggestionsRequestAction> actions,
  EpicStore<AppState> store,
) =>
    actions.asyncMap((action) async {
      return SearchSuggestionsResponseAction(action.query, Type.values);
    });
