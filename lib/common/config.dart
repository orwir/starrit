/// Specifies static app capabilities, build variables, etc.
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

  Config._();
}
