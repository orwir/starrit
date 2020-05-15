import 'package:flutter/foundation.dart';
import 'package:http/http.dart';
import 'package:starrit/feed/model/feed.dart';

Future<Response> listing({
  @required String baseUrl,
  @required Feed feed,
  String after,
  String before,
  int limit,
}) async {
  final url = '$baseUrl$feed.json'
      '?after=$after'
      '&before=$before'
      '&limit=$limit'
      '&include_categories=true'
      '&sr_detail=true'
      '&raw_json=1';
  return await get(url);
}
