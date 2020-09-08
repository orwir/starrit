import 'package:flutter/foundation.dart';

/// Build constants.
class Env {
  /// Client ID from Reddit.
  ///
  /// Follow this [instruction](https://github.com/reddit-archive/reddit/wiki/OAuth2) to get it.
  ///
  /// Add compile-time arg:
  /// ```--dart-define=client_id=<YOUR_CLIENT_ID>```
  static String get clientID =>
      TestEnv.clientID ??
      String.fromEnvironment('client_id', defaultValue: null);

  /// True if [clientID] exists.
  static bool get supportAuthorization => clientID != null;

  /// Redirect URI for OAuth request.
  ///
  /// Catched by native iOS and Android envs and forwards to Flutter.
  static String get authRedirectUri =>
      TestEnv.authRedirectUri ?? 'starrit://authorization';

  /// URL to get data from Reddit without authorization.
  static String get baseAnonymousUrl =>
      TestEnv.baseAnonymousUrl ?? 'https://reddit.com';

  /// URL to get data from Reddit with authorization.
  static String get baseAuthorizedUrl =>
      TestEnv.baseAuthorizedUrl ?? 'https://oauth.reddit.com';

  Env._();
}

@visibleForTesting
class TestEnv {
  static String clientID;
  static String authRedirectUri;
  static String baseAnonymousUrl;
  static String baseAuthorizedUrl;

  TestEnv._();
}
