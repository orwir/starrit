import 'package:flutter/foundation.dart';
import 'package:http/http.dart';
import 'package:starrit/common/models/feed.dart';

Future<Response> listing({
  @required String domain,
  @required Feed feed,
  String after,
  String before,
  int limit,
}) async {
  final url = 'https://$domain$feed.json'
      '?after=$after'
      '&before=$before'
      '&limit=$limit'
      '&include_categories=true'
      '&sr_detail=true'
      '&raw_json=1';
  return await get(url);
}
