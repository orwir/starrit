import 'dart:convert';
import 'dart:io';

import 'package:redux_epics/redux_epics.dart';
import 'package:rxdart/rxdart.dart';
import 'package:starrit/common/models/state.dart';
import 'package:starrit/common/utils/json.dart';
import 'package:starrit/feed/models/feed.dart';
import 'package:starrit/search/service.dart';

import 'actions.dart';

final epic = combineEpics<AppState>([
  TypedEpic<AppState, SearchSuggestionsRequestAction>(_requestSuggestions),
]);

Stream<dynamic> _requestSuggestions(
  Stream<SearchSuggestionsRequestAction> actions,
  EpicStore<AppState> store,
) {
  Stream<dynamic> fetch(String query) async* {
    if (query.length < 3) {
      yield SearchSuggestionsResponseAction(query, Type.values);
    } else {
      try {
        final response = await subreddits(domain: 'reddit.com', query: query);
        if (response.statusCode != 200) {
          throw HttpException(
            'Usuccessful suggestions request. Code: ${response.statusCode}',
          );
        }
        final Map<String, dynamic> result = jsonDecode(response.body);
        final suggestions =
            result.get<List>('names').map((s) => Type.subreddit(s)).toList();

        yield SearchSuggestionsResponseAction(query, suggestions);
      } on Exception {
        // TODO: handle exceptions properly
        yield SearchSuggestionsResponseAction(query, Type.values);
      }
    }
  }

  final dispose = actions.where((action) =>
      action is SearchDisposeAction ||
      action is SearchSuggestionsRequestAction);

  return actions
      .debounceTime(Duration(milliseconds: 250))
      .switchMap((action) => fetch(action.query).takeUntil(dispose));
}
