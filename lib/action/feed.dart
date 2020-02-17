import 'package:flutter/foundation.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

@immutable
class LoadLatestFeedAction {}

@immutable
class LoadFeedDataAction {
  final Feed feed;

  LoadFeedDataAction(this.feed);
}

@immutable
class FeedDataLoadSucceededAction {
  final Feed feed;
  final List<Post> posts;

  FeedDataLoadSucceededAction(this.feed, this.posts);
}

@immutable
class FeedDataLoadFailedAction {
  final Feed feed;
  final Exception exception;

  FeedDataLoadFailedAction(this.feed, this.exception);
}
