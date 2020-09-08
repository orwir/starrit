import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';

/// Save last open feed.
@immutable
class LastFeed {
  /// Last open feed.
  final Feed feed;

  LastFeed(this.feed);

  @override
  String toString() => '$runtimeType { $feed }';
}
