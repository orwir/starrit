import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/model/token.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/feed/model/chunk.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

Future<Chunk> loadFeedChunk(
  Feed feed,
  Access access, {
  String after,
  Token token,
}) async {
  final url = '${access.baseUrl}$feed.json'
      '?after=$after'
      '&include_categories=true'
      '&sr_detail=true'
      '&raw_json=1';
  final response = await get(url);
  if (response.statusCode != 200) {
    throw HttpException(response.reasonPhrase);
  }
  final Map<String, dynamic> result = jsonDecode(response.body);
  final posts = result
      .get<List>('data.children')
      .map((json) => Post.fromJson(json['data']))
      .toList(growable: false);
  final next = result.string('data.after');
  return Chunk(posts, next);
}
