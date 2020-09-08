import 'dart:convert';

import 'package:starrit/common/network.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/feed/model/feed.dart';

Future<List<FeedType>> loadSuggestions(Network network, String query) async {
  if ((query?.length ?? 0) < 3) return FeedType.values;

  final url = '/api/search_reddit_names.json'
      '?query=$query'
      '&include_over_18=true'
      '&include_unadvertisable=true';
  final response = await network.get(url);
  final Map<String, dynamic> json = jsonDecode(response.body);
  return json
      .get<List>('names')
      .whereType<String>()
      .map(FeedType.subreddit)
      .toList(growable: false);
}
