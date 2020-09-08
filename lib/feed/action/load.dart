import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

/// Requests data for the [Feed].
///
/// * Creates [FeedState] if not exists.
/// * Sets [FeedState] to [Status.processing].
/// * Wipes posts and next if [reset] is true.
/// * Starts epic which runs [loadFeedData].
@immutable
class LoadFeed {
  /// Loads data for this specific [Feed].
  final Feed feed;

  /// If not null obtains posts after this ID.
  final String after;

  /// If true wipes previously loaded data.
  final bool reset;

  LoadFeed(this.feed, {this.after, this.reset = false})
      : assert(feed != null),
        assert(reset != null);

  @override
  String toString() =>
      '$runtimeType { $feed${after == null ? '' : ', after:$after'}${reset ? ', reset' : ''} }';
}

/// Successful response for feed request.
///
/// * Sets [FeedState] into [Status.success].
/// * Updates [posts] and [next].
/// * Removes exception from [FeedState].
@immutable
class LoadFeedSuccess {
  /// Data belongs to this [Feed].
  final Feed feed;

  /// Chunk of requested data.
  final Iterable<Post> posts;

  /// ID to get next chunk of data.
  final String next;

  LoadFeedSuccess(this.feed, this.posts, this.next)
      : assert(feed != null),
        assert(posts != null);

  @override
  String toString() =>
      '$runtimeType { $feed, next:$next, posts:${posts.length} }';
}

/// Unsuccessful reponse for feed request.
///
/// * Sets [FeedState] into [Status.failure].
/// * Adds [exception].
@immutable
class LoadFeedFailure {
  /// Data belongs to this [Feed].
  final Feed feed;

  /// Error information.
  final Exception exception;

  LoadFeedFailure(this.feed, this.exception)
      : assert(feed != null),
        assert(exception != null);

  @override
  String toString() => '$runtimeType { $feed, exception:$exception }';
}
