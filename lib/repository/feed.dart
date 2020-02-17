import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

class FeedRepository {
  Future<Feed> latestFeed() async {
    return Future.delayed(Duration(seconds: 1), () => Feed.home());
  }

  Future<List<Post>> fetchFeedData(Feed feed) async {
    return Future.delayed(Duration(seconds: 2), () => <Post>[]);
  }
}
