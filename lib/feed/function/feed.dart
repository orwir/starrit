import 'dart:convert';

import 'package:starrit/common/network.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

/// Load chunk of feed data.
Future<FeedChunk> loadFeedChunk(
  Network network,
  Feed feed, {
  String after,
  String before,
  int limit,
}) async {
  final url = '${feed.path}.json'
      '?after=$after'
      '&before=$before'
      '&limit=$limit'
      '&include_categories=true'
      '&sr_detail=true'
      '&raw_json=1';
  final response = await network.get(url);
  final Map<String, dynamic> json = jsonDecode(response.body);
  final posts = json
      .get<List>('data.children')
      .map((child) => Post.fromJson(child['data']))
      .toList(growable: false);
  final next = json.string('data.after');

  return FeedChunk(posts, next);
}
