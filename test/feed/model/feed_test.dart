import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/feed/model/feed.dart';

void main() {
  test(
    'Feed constructor should create unique object when invoked with the same parameters.',
    () {
      final feed1 = Feed(FeedType.all, FeedSort.best);
      final feed2 = Feed(FeedType.all, FeedSort.best);

      expect(feed1, isNot(feed2));
    },
  );

  test(
    'Type.subreddit(test) should return Type with the path "/r/test".',
    () {
      final type = FeedType.subreddit('test');

      expect(type.path, '/r/test');
    },
  );
}
