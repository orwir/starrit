import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/action/blur_nsfw.dart';
import 'package:starrit/settings/action/last_feed.dart';

import '../helper.dart';

void main() {
  test('BlurNsfw should update blurNsfw when dispatched.', () {
    final store = testStore();

    expect(store.state.blurNsfw, AppState.initial.blurNsfw);

    store.dispatch(BlurNsfw(!AppState.initial.blurNsfw));

    expect(store.state.blurNsfw, !AppState.initial.blurNsfw);
  });

  test('LastFeed should update lastFeed when dispatched.', () {
    final store = testStore();
    final feed = Feed(FeedType.subreddit('r/test'), FeedSort.controversial);

    expect(store.state.lastFeed, AppState.initial.lastFeed);

    store.dispatch(LastFeed(feed));

    expect(store.state.lastFeed, feed);
  });
}
