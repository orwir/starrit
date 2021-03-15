import 'dart:convert';

import 'package:starrit/common/service.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

mixin FeedService on Service {
  Future<FeedChunk> loadFeedChunk(
    Feed feed, {
    String before,
    String after,
    int limit,
  }) async {
    final url = '${feed.path}.json'
        '?before=$before'
        '&after=$after'
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
}
