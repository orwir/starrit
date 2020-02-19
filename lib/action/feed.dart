import 'package:flutter/foundation.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

@immutable
class PostsLoadingStartAction {
  final Feed feed;
  final String after;
  final String before;
  final int limit;

  PostsLoadingStartAction(
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
class PostsLoadingSuccessAction {
  final Feed feed;
  final List<Post> posts;

  PostsLoadingSuccessAction(this.feed, this.posts);

  @override
  String toString() => '{feed: $feed, posts: ${posts.length}}';
}

@immutable
class PostsLoadingFailureAction {
  final Feed feed;
  final Exception error;

  PostsLoadingFailureAction(this.feed, this.error);

  @override
  String toString() => '{feed: $feed, error: "$error"}';
}
