import 'dart:convert';
import 'dart:io';

import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:starrit/feed/service.dart';

Future<dynamic> loadFeedData(LoadFeedData request, Access access) async {
  try {
    final response = await listing(
      baseUrl: access.baseUrl,
      feed: request.feed,
      after: request.after,
    );
    if (response.statusCode != 200) {
      throw HttpException(
          'Usuccessful feed request. Code: ${response.statusCode}');
    }
    final Map<String, dynamic> result = jsonDecode(response.body);
    final posts = result
        .get<List>('data.children')
        .map((json) => Post.fromJson(json['data']))
        .toList();
    final next = result.string('data.after');

    return LoadFeedDataSuccess(request.feed, posts, next);
  } on Exception catch (e) {
    return LoadFeedDataFailure(request.feed, e);
  }
}
