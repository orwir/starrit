import 'package:flutter/foundation.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

/// Requests data for the [Feed].
///
/// * Creates [FeedState] if not exists.
/// * Sets [FeedState] to [StateStatus.processing].
/// * Wipes posts and next if [reset] is true.
/// * Starts epic which runs [loadFeedData].
@immutable
class LoadFeedData {
  /// Loads data for this specific [Feed].
  final Feed feed;

  /// If not null obtains posts after this ID.
  final String after;

  /// If true wipes previous posts and next.
  final bool reset;

  LoadFeedData(this.feed, {this.after, this.reset = false})
      : assert(feed != null),
        assert(reset != null);

  @override
  String toString() =>
      '$runtimeType { $feed${after == null ? '' : ', after:$after'}${reset ? ', reset' : ''} }';
}

/// Successful response for feed request.
///
/// * Sets [FeedState] into [StateStatus.success].
/// * Updates [posts] and [next].
/// * Removes exception from [FeedState].
@immutable
class LoadFeedDataSuccess {
  /// Data belongs to this [Feed].
  final Feed feed;

  /// Chunk of requested data.
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

/// Unsuccessful reponse for feed request.
///
/// * Sets [FeedState] into [StateStatus.failure].
/// * Adds [exception].
@immutable
class LoadFeedDataFailure {
  /// Data belongs to this [Feed].
  final Feed feed;

  /// Error information.
  final Exception exception;

  LoadFeedDataFailure(this.feed, this.exception)
      : assert(feed != null),
        assert(exception != null);

  @override
  String toString() => '$runtimeType { $feed, exception:$exception }';
}

/// Removes a [FeedState] belonged to the [Feed].
///
/// Dispatches when [FeedScreen] is closed and corresponded data is no longer needed.
@immutable
class DisposeFeedData {
  /// Feed to remove.
  final Feed feed;

  DisposeFeedData(this.feed) : assert(feed != null);

  @override
  String toString() => '$runtimeType { $feed }';
}
