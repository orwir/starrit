/// Determine access state of user.
enum Access { unspecified, revoked, authorized, anonymous }

extension AccessExtensions on Access {
  /// Resolve base url for data access on Reddit.com
  String get baseUrl => this == Access.authorized
      ? 'https://oauth.reddit.com'
      : 'https://www.reddit.com';

  /// Whether user state is stable (authorized or anonymous).
  bool get stable => this == Access.authorized || this == Access.anonymous;

  /// For debug purposes.
  String get label => toString().split('.')[1];
}
