import 'package:starrit/access/model/duration.dart';
import 'package:starrit/access/model/scope.dart';
import 'package:starrit/env.dart';
import 'package:url_launcher/url_launcher.dart';

final scope = [
  Scope.identity,
  Scope.read,
].map((s) => s.parameter).join(',');

Future<void> openAuthorizationService(String requestState) async {
  final requestUri = 'https://www.reddit.com/api/v1/authorize.compact'
      '?client_id=${Config.clientID}'
      '&response_type=code'
      '&state=$requestState'
      '&redirect_uri=starrit://authorization'
      '&duration=${TokenDuration.permanent.parameter}'
      '&scope=$scope';

  if (await canLaunch(requestUri)) launch(requestUri);
}
