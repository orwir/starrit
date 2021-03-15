import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';

/// The application preferences.
///
/// Uses as transfer object during app initialization process.
@immutable
class Preferences {
  /// User's access level.
  final Access access;

  /// Last visible feed.
  ///
  /// The value is used to reopen this feed on app start up.
  final Feed lastFeed;

  /// Controls whether NSFW content should be blurred.
  final bool blurNsfw;

  Preferences({
    @required this.access,
    @required this.lastFeed,
    @required this.blurNsfw,
  });
}
