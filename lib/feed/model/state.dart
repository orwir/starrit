import 'package:flutter/foundation.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

/// Feed state.
@immutable
class FeedState {
  /// Feed metadata.
  final Feed feed;

  /// Current status of data obtaining process.
  final StateStatus status;

  /// Page of requested data.
  final List<Post> posts;

  /// ID to get next chunk of data.
  final String next;

  /// Error information.
  final Exception exception;

  FeedState({
    @required this.feed,
    @required this.status,
    @required this.posts,
    this.next,
    this.exception,
  })  : assert(feed != null),
        assert(status != null),
        assert(posts != null);

  FeedState toLoading({bool reset = false}) => FeedState(
        feed: feed,
        status: StateStatus.loading,
        posts: reset ? const [] : posts,
        next: reset ? null : next,
      );

  FeedState toSuccess(List<Post> posts, String next) => FeedState(
        feed: feed,
        status: StateStatus.success,
        posts: this.posts + posts,
        next: next,
      );

  FeedState toFailure(Exception exception) => FeedState(
        feed: feed,
        status: StateStatus.success,
        exception: exception,
        posts: posts,
        next: next,
      );

  @override
  String toString() =>
      '{ ' +
      [
        'feed:$feed',
        status == StateStatus.loading ? 'loading' : '',
        next == null ? '' : 'next:$next',
        'posts:${posts.length}',
        exception == null ? '' : 'exception:$exception',
      ].where((line) => line.isNotEmpty).join(', ') +
      ' }';

  @override
  int get hashCode => hash([feed, status, posts, next, exception]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedState &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          status == other.status &&
          posts == other.posts &&
          next == other.next &&
          exception == other.exception;
}
