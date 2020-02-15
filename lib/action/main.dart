import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

class LoadLatestFeedAction {}

class FeedDataLoadedAction {
  final Feed feed;
  final List<Post> posts;

  FeedDataLoadedAction(this.feed, this.posts);
}
