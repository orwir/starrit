import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

/// Request feed data.
@immutable
class LoadFeedData {
  /// Feed metadata.
  final Feed feed;

  /// Item ID to get data starting from.
  final String after;

  /// Whether replace previous data or add.
  final bool reset;

  LoadFeedData(this.feed, {this.after, this.reset = false})
      : assert(feed != null),
        assert(reset != null);

  @override
  String toString() =>
      '$runtimeType { $feed${after == null ? '' : ', after:$after'}${reset ? ', reset' : ''} }';
}

/// Successful response with a piece of requested feed data.
@immutable
class LoadFeedDataSuccess {
  /// Feed metadata.
  final Feed feed;

  /// Page of requested data.
  final List<Post> posts;

  /// ID to get next chunk of data.
  final String next;

  LoadFeedDataSuccess(this.feed, this.posts, this.next)
      : assert(feed != null),
        assert(posts != null),
        assert(next != null);

  @override
  String toString() =>
      '$runtimeType { $feed, next:$next, posts:${posts.length} }';
}

/// Unsuccessful reponse with the cause of an error.
@immutable
class LoadFeedDataFailure {
  /// Feed metadata.
  final Feed feed;

  /// Error information.
  final Exception exception;

  LoadFeedDataFailure(this.feed, this.exception)
      : assert(feed != null),
        assert(exception != null);

  @override
  String toString() => '$runtimeType { $feed, exception:$exception }';
}

/// Request to delete data from the storage.
/// Occurs when Feed Screen is closed.
@immutable
class DisposeFeedData {
  /// Feed metadata.
  final Feed feed;

  DisposeFeedData(this.feed) : assert(feed != null);

  @override
  String toString() => '$runtimeType { $feed }';
}
