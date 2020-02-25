import 'package:flutter/foundation.dart';
import 'package:starrit/extensions/object.dart';

import 'feed.dart';
import 'post.dart';

@immutable
class AppState {
  final Feed feed;
  final bool loading;
  final bool blurNsfw;
  final List<Post> posts;
  final Exception exception;

  AppState({
    @required this.feed,
    @required this.loading,
    @required this.blurNsfw,
    @required this.posts,
    @required this.exception,
  });

  AppState.initial()
      : this(
          feed: Feed.home(),
          loading: false,
          blurNsfw: false,
          posts: const <Post>[],
          exception: null,
        );

  AppState copyWith({
    Feed feed,
    bool loading,
    bool blurNsfw,
    List<Post> posts,
    Exception exception,
  }) =>
      AppState(
        feed: feed ?? this.feed,
        loading: loading ?? this.loading,
        blurNsfw: blurNsfw ?? this.blurNsfw,
        posts: posts ?? this.posts,
        exception: exception ?? this.exception,
      );

  @override
  String toString() =>
      '$runtimeType[feed=$feed, loading=$loading, blurNsfw=$blurNsfw, posts=${posts.length}, exception=$exception]';

  @override
  int get hashCode => hash([feed, loading, blurNsfw, posts, exception]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is AppState &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          loading == other.loading &&
          blurNsfw == other.blurNsfw &&
          posts == other.posts &&
          exception == other.exception;
}
