import 'package:flutter/foundation.dart';

import 'models/feed.dart';
import 'models/post.dart';

@immutable
class FeedRequestAction {
  FeedRequestAction(this.feed, {this.reset = false, this.after});

  final Feed feed;
  final bool reset;
  final String after;

  @override
  String toString() =>
      '{type:$runtimeType, feed:$feed, reset:$reset${after != null ? ", after:$after" : ""}}';
}

@immutable
class FeedDisposeAction {
  FeedDisposeAction(this.feed);

  final Feed feed;

  @override
  String toString() => '{type:$runtimeType, feed:$feed}';
}

@immutable
class FeedResponseSuccessAction {
  FeedResponseSuccessAction(this.feed, this.posts, this.next);

  final Feed feed;
  final Iterable<Post> posts;
  final String next;

  @override
  String toString() =>
      '{type:$runtimeType, feed:$feed, posts:${posts.length}, next:$next}';
}

@immutable
class FeedResponseFailureAction {
  FeedResponseFailureAction(this.feed, this.exception);

  final Feed feed;
  final Exception exception;

  @override
  String toString() => '{type:$runtimeType, feed:$feed, exception:$exception}';
}
