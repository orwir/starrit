import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/search/actions.dart';
import 'package:starrit/feed/model/feed.dart';

Future<List<Type>> loadSuggestions(
    LoadSuggestions request, Access access) async {
  if (request.query.length < 3) {
    return Type.values;
  }
  final url = '${access.baseUrl}/api/search_reddit_names.json'
      '?query=${request.query}'
      '&include_over_18=true'
      '&include_unadvertisable=true';
  final response = await get(url);
  if (response.statusCode != 200) {
    throw HttpException(response.reasonPhrase);
  }
  final Map<String, dynamic> result = jsonDecode(response.body);
  return result
      .get<List<String>>('names')
      .map(Type.subreddit)
      .toList(growable: false);
}
