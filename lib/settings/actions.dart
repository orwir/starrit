import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';

/// Load app preferences.
class LoadPreferences {
  @override
  String toString() => '$runtimeType';
}

/// Preferences successfully obtained.
class LoadPreferencesSuccess {
  /// Latest visible feed.
  final Feed latestFeed;

  /// Whether show NSFW content blurred.
  final bool blurNsfw;

  /// Access status.
  final Access access;

  LoadPreferencesSuccess({
    @required this.latestFeed,
    @required this.blurNsfw,
    @required this.access,
  })  : assert(latestFeed != null),
        assert(blurNsfw != null);

  @override
  String toString() =>
      '$runtimeType { latestFeed:$latestFeed, blurNsfw:$blurNsfw, access:${access.label} }';
}

/// Preferences loading failed.
class LoadPreferencesFailure {
  /// Cause of failed loading.
  final Exception exception;

  LoadPreferencesFailure(this.exception) : assert(exception != null);

  @override
  String toString() => '$runtimeType { $exception }';
}

/// Save latest feed to preferences.
class UpdateLatestFeed {
  /// Latest visible feed.
  final Feed feed;

  UpdateLatestFeed(this.feed) : assert(feed != null);

  @override
  String toString() => '$runtimeType { $feed }';
}

/// Change state of Blur NSFW flag
class UpdateBlurNsfw {
  /// Whether show NSFW content blurred.
  final bool blurNsfw;

  UpdateBlurNsfw(this.blurNsfw) : assert(blurNsfw != null);

  @override
  String toString() => '$runtimeType { $blurNsfw }';
}

class PreferencesUpdateSuccess {
  @override
  String toString() => '$runtimeType';
}
