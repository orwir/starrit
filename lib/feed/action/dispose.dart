import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';

/// Removes a [FeedState] belonged to the [Feed].
///
/// Dispatches when [FeedScreen] is closed and corresponded data is no longer needed.
@immutable
class DisposeFeed {
  /// Feed to remove.
  final Feed feed;

  DisposeFeed(this.feed) : assert(feed != null);

  @override
  String toString() => '$runtimeType { $feed }';
}
