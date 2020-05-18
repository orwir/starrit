import 'dart:convert';
import 'dart:io';

import 'package:starrit/common/util/json.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/search/actions.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/search/service.dart';

Future<dynamic> loadSuggestions(LoadSuggestions request, Access access) async {
  if (request.query.length < 3) {
    return LoadSuggestionsSuccess(Type.values);
  }
  try {
    final response = await subreddits(
      baseUrl: access.baseUrl,
      query: request.query,
    );
    if (response.statusCode != 200) {
      throw HttpException(
          'Usuccessful suggestions request. Code: ${response.statusCode}');
    }
    final Map<String, dynamic> result = jsonDecode(response.body);
    final suggestions =
        result.get<List<String>>('names').map(Type.subreddit).toList();

    return LoadSuggestionsSuccess(suggestions);
  } on Exception catch (e) {
    return LoadSuggestionsFailure(e);
  }
}
