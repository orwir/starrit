import 'package:starrit/env.dart';

/// Determine access status of the user.
enum Access {
  /// User hasn't yet specified desired access type.
  unspecified,

  /// User revoked access to his account.
  revoked,

  /// User decided to not connect his Reddit-account.
  anonymous,

  /// User successfully connected his Reddit-account.
  authorized
}

extension AccessExtensions on Access {
  /// Resolve base url for data access on Reddit.com
  String get baseUrl => this == Access.authorized || this == Access.revoked
      ? Env.baseAuthorizedUrl
      : Env.baseAnonymousUrl;

  /// Whether user state is stable (authorized or anonymous).
  bool get stable => this == Access.authorized || this == Access.anonymous;
}
