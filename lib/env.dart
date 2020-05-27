/// Specifies app capabilities, build variables, static config data, etc.
class Config {
  /// Whether application (this specific build) supports authorization functionality.
  static const supportAuthorization = bool.hasEnvironment('client_id');

  /// Client ID for Reddit API.
  ///
  /// If not present authorization features disabled.
  ///
  /// To get one you have to:
  /// 1. Follow an [insturction](https://github.com/reddit-archive/reddit/wiki/OAuth2).
  /// 2. Pass compile argument 'client_id':
  ///
  ///    --dart-define=client_id=<YOUR_CLIENT_ID>
  /// 3. Enjoy.
  static const clientID = String.fromEnvironment('client_id');

  /// Redirect URI for OAuth request.
  static const authResponseUri = 'starrit://authorization';

  /// Base url to get data from Reddit if user authorized.
  static const baseUrlAuthorized = 'https://oauth.reddit.com';

  /// Base url to get data from Reddit if user anonymous.
  static const baseUrlAnonymous = 'https://www.reddit.com';

  Config._();
}
