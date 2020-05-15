import 'dart:convert';
import 'dart:io';

import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/search/actions.dart';
import 'package:starrit/search/service.dart';
import 'package:starrit/access/model/access.dart';

final Epic<AppState> searchEpic = combineEpics([
  _loadSuggestions,
]);

Stream<dynamic> _loadSuggestions(
  Stream<dynamic> actions,
  EpicStore<AppState> store,
) {
  Future<dynamic> fetch(LoadSuggestions request) async {
    if (request.query.length < 3) {
      return LoadSuggestionsSuccess(Type.values);
    }
    try {
      final response = await subreddits(
        baseUrl: store.state.access.baseUrl,
        query: request.query,
      );
      if (response.statusCode != 200) {
        throw HttpException(
          'Usuccessful suggestions request. Code: ${response.statusCode}',
        );
      }
      final Map<String, dynamic> result = jsonDecode(response.body);
      final suggestions =
          result.get<List>('names').map((s) => Type.subreddit(s)).toList();

      return LoadSuggestionsSuccess(suggestions);
    } on Exception catch (e) {
      return LoadSuggestionsFailure(e);
    }
  }

  final dispose = actions.where(
      (action) => action is LoadSuggestions || action is DisposeSearchData);

  return actions
      .whereType<LoadSuggestions>()
      .debounceTime(Duration(milliseconds: 250))
      .switchMap((action) => fetch(action).asStream().takeUntil(dispose));
}
