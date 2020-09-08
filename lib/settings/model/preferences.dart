import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';

/// Preferences of the app.
///
/// Used to initialize application.
@immutable
class Preferences {
  /// User's access status.
  final Access access;

  /// Last visible feed.
  ///
  /// The value is used to reopen on app start last feed user opened.
  final Feed lastFeed;

  /// If true NSFW-content will be blurred by default.
  final bool blurNsfw;

  Preferences({
    @required this.access,
    @required this.lastFeed,
    @required this.blurNsfw,
  });
}
