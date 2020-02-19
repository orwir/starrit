import 'package:flutter/foundation.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

@immutable
class AppState {
  final FeedState feedState;

  AppState({this.feedState});

  AppState.initial()
      : this(
          feedState: FeedState.loaded(
            feed: Feed.home(),
            posts: const <Post>[],
          ),
        );

  AppState copyWith({FeedState feedState}) => AppState(
        feedState: feedState ?? this.feedState,
      );

  @override
  String toString() => '{$runtimeType: {feedState: $feedState}}';

  @override
  int get hashCode => feedState?.hashCode ?? 0;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is AppState &&
          runtimeType == other.runtimeType &&
          feedState == other.feedState;
}

@immutable
class FeedState {
  final Feed feed;
  final List<Post> posts;
  final bool loading;
  final Exception error;

  FeedState({
    @required this.feed,
    @required this.loading,
    this.posts = const <Post>[],
    this.error,
  });

  FeedState.loading({Feed feed, List<Post> posts})
      : this(
          feed: feed,
          posts: posts,
          loading: true,
          error: null,
        );

  FeedState.loaded({Feed feed, List<Post> posts})
      : this(
          feed: feed,
          posts: posts,
          loading: false,
          error: null,
        );

  FeedState.failed({Feed feed, List<Post> posts, Exception error})
      : this(
          feed: feed,
          posts: posts,
          loading: false,
          error: error,
        );

  @override
  String toString() =>
      '{feed: $feed, posts: ${posts.length}, loading: $loading, error: "$error"}';

  @override
  int get hashCode =>
      feed.hashCode ^
      posts.hashCode ^
      loading.hashCode ^
      (error?.hashCode ?? 0);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedState &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          loading == other.loading &&
          posts == other.posts &&
          error == other.error;
}
