import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';

@immutable
class Preferences {
  /// Whether blur NSFW content.
  final bool blurNsfw;

  /// Latest visible feed.
  final Feed latestFeed;

  /// User access status.
  final Access access;

  Preferences({
    @required this.blurNsfw,
    @required this.latestFeed,
    @required this.access,
  })  : assert(blurNsfw != null),
        assert(latestFeed != null),
        assert(access != null);

  @override
  String toString() =>
      'blurNsfw:$blurNsfw, latestFeed:$latestFeed, access:$access';
}
