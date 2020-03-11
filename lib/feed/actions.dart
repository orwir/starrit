import 'package:flutter/foundation.dart';
import 'package:starrit/common/utils/object.dart';

import 'models/feed.dart';
import 'models/post.dart';

@immutable
class FeedRequestAction {
  FeedRequestAction(this.feed, {this.reset = false, this.after})
      : assert(feed != null),
        assert(reset != null);

  final Feed feed;
  final bool reset;
  final String after;

  @override
  String toString() =>
      '{type:$runtimeType, feed:$feed, reset:$reset${after != null ? ", after:$after" : ""}}';

  @override
  int get hashCode => hash([feed, reset, after]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedRequestAction &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          reset == other.reset &&
          after == other.after;
}

@immutable
class FeedDisposeAction {
  FeedDisposeAction(this.feed) : assert(feed != null);

  final Feed feed;

  @override
  String toString() => '{type:$runtimeType, feed:$feed}';

  @override
  int get hashCode => hash([feed]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedDisposeAction &&
          runtimeType == other.runtimeType &&
          feed == other.feed;
}

@immutable
class FeedResponseSuccessAction {
  FeedResponseSuccessAction(this.feed, this.posts, this.next)
      : assert(feed != null),
        assert(posts != null);

  final Feed feed;
  final Iterable<Post> posts;
  final String next;

  @override
  String toString() =>
      '{type:$runtimeType, feed:$feed, posts:${posts.length}, next:$next}';

  @override
  int get hashCode => hash([feed, posts, next]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedResponseSuccessAction &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          posts == other.posts &&
          next == other.next;
}

@immutable
class FeedResponseFailureAction {
  FeedResponseFailureAction(this.feed, this.exception)
      : assert(feed != null),
        assert(exception != null);

  final Feed feed;
  final Exception exception;

  @override
  String toString() => '{type:$runtimeType, feed:$feed, exception:$exception}';

  @override
  int get hashCode => hash([feed, exception]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedResponseFailureAction &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          exception == other.exception;
}
