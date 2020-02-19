import 'package:flutter/foundation.dart';
import 'package:http/http.dart';
import 'package:starrit/model/feed.dart';

Future<Response> listing({
  @required Client client,
  @required String domain,
  @required Feed feed,
  String after,
  String before,
  int limit,
}) async {
  final url = 'https://$domain${feed.asParameter}.json'
      '?after=$after'
      '&before=$before'
      '&limit=$limit'
      '&include_categories=true'
      '&sr_detail=true'
      '&raw_json=1';
  return await client.get(url);
}

Future<Response> suggestions({
  @required Client client,
  @required String domain,
  @required String query,
}) async {
  final url = 'https://$domain/api/search_reddit_names.json'
      '?query=$query'
      '&include_over_18=true'
      '&include_unadvertisable=true';
  return await client.get(url);
}
