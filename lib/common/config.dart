class Config {
  /// Whether account specific features can be enabled.
  static const hasAccessMode = bool.hasEnvironment('client_id');

  /// Client ID for Reddit API. Without it auth features disabled.
  /// To get one you have to:
  /// 1. go to https://github.com/reddit-archive/reddit/wiki/OAuth2 and follow an instruction.
  /// 2. put client_id as a parameter to flutter arguments:
  ///    --dart-define=client_id=<YOUR_CLIENT_ID>
  ///
  /// VS Code: https://code.visualstudio.com/docs/editor/debugging
  static const clientID = String.fromEnvironment('client_id');

  Config._();
}
