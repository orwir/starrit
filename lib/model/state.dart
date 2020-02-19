import 'package:flutter/foundation.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

@immutable
class AppState {
  final FeedState feedState;

  AppState({this.feedState});

  AppState copyWith({FeedState feedState}) => AppState(
        feedState: feedState ?? this.feedState,
      );
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
    this.posts,
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
}
