import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/post.dart';

class Startup {
  @override
  String toString() => '$runtimeType';
}

/// Initialization completed successfully.
class StartupSuccess {
  /// User authorization type.
  final Access access;

  /// Whether blur NSFW content or not (images, video, gifs).
  final bool blurNsfw;

  /// Cached feed (latest open by user).
  final Feed feed;

  /// Prefetched posts for [feed].
  final List<Post> posts;

  /// ID to load next chunk of feedd data.
  final String next;

  StartupSuccess({
    @required this.access,
    @required this.blurNsfw,
    @required this.feed,
    @required this.posts,
    this.next,
  })  : assert(access != null),
        assert(blurNsfw != null),
        assert(feed != null),
        assert(posts != null);

  @override
  String toString() =>
      '$runtimeType { access:$access, ${blurNsfw ? 'blurNsfw,' : ''} '
      'feed:$feed, posts:${posts.length}, ${next != null ? 'next:$next' : ''} }';
}

/// Initialization completed with an error.
class StartupFailure {
  /// Cause of the failure.
  final Exception exception;

  StartupFailure(this.exception) : assert(exception != null);

  @override
  String toString() => '$runtimeType { $exception }';
}
