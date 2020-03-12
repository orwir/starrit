import 'package:flutter/foundation.dart';
import 'package:http/http.dart';

Future<Response> subreddits({
  @required String domain,
  @required String query,
}) async {
  final url = 'https://$domain/api/search_reddit_names.json'
      '?query=$query'
      '&include_over_18=true'
      '&include_unadvertisable=true';
  return await get(url);
}

// Future<Response> subreddits({
//   @required String domain,
//   @required String query,
// }) async {
//   final url = 'https://$domain/api/search_subreddits.json'
//       '?query=$query'
//       '&include_over_18=true'
//       '&include_unadvertisable=true'
//       '&raw_json=1';
//   return await post(url);
// }
