import 'package:flutter/foundation.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

@immutable
class AppState {
  final latestFeed;
  final Map<Feed, FeedState> feeds;

  AppState({
    @required this.latestFeed,
    this.feeds = const {},
  });

  AppState.initial()
      : this(
          latestFeed: Feed.home(),
          feeds: {Feed.home(): FeedState(const [])},
        );

  AppState copyWith({
    Feed latestFeed,
    Map<Feed, FeedState> feeds,
  }) {
    return AppState(
      latestFeed: latestFeed ?? this.latestFeed,
      feeds: feeds ?? this.feeds,
    );
  }
}

@immutable
class FeedState {
  final List<Post> posts;

  FeedState(this.posts);
}
