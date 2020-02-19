import 'package:flutter/foundation.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

@immutable
class LoadPostsAction {
  final Feed feed;
  final String after;
  final String before;
  final int limit;

  LoadPostsAction(
    this.feed, {
    @required this.after,
    @required this.before,
    @required this.limit,
  });

  @override
  String toString() =>
      '{feed: $feed, after: "$after", before: "$before", limit: "$limit"}';
}

@immutable
class LoadPostsSuccessAction {
  final Feed feed;
  final List<Post> posts;

  LoadPostsSuccessAction(this.feed, this.posts);

  @override
  String toString() => '{feed: $feed, posts: ${posts.length}}';
}

@immutable
class LoadPostsFailureAction {
  final Feed feed;
  final Exception error;

  LoadPostsFailureAction(this.feed, this.error);

  @override
  String toString() => '{feed: $feed, error: "$error"}';
}
