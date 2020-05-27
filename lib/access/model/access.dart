import 'package:starrit/env.dart';

/// Determine access status.
enum Access {
  ///  A user hasn't made a decision yet or app
  /// doesn't support authorization functionality:
  /// [Config.supportAuthorization] == false.
  unspecified,

  /// A user passed authorization but for a reason nullified it.
  revoked,

  /// A user successfully authorized and has a valid auth token.
  authorized,

  /// A user made a decision to stay anonymous.
  anonymous,
}

extension AccessExtensions on Access {
  /// Resolve base url for data access on Reddit.com
  String get baseUrl => this == Access.authorized
      ? Config.baseUrlAuthorized
      : Config.baseUrlAnonymous;

  /// Whether user state is stable (authorized or anonymous).
  bool get stable => this == Access.authorized || this == Access.anonymous;

  /// For debug purposes.
  String get label => toString().split('.')[1];
}
