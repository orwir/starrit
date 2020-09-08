import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/action/dispose.dart';
import 'package:starrit/feed/action/load.dart';
import 'package:starrit/feed/model/feed.dart';

import '../helper.dart';

void main() {
  test('LoadFeed should create & initialize FeedState', () {
    final store = testStore();
    final action = LoadFeed(Feed(FeedType.home, FeedSort.best));

    expect(store.state.feeds[action.feed], isNull);

    store.dispatch(action);

    final state = store.state.feeds[action.feed];
    expect(state, isNotNull);
    expect(state.status, Status.processing);
    expect(state.exception, isNull);
    expect(state.posts.length, 0);
    expect(state.next, isNull);
  });

  test('LoadFeed should nullify exception when update existed state', () {
    final store = testStore();
    final feed = Feed(FeedType.home, FeedSort.best);

    store.dispatch(LoadFeed(feed));
    store.dispatch(LoadFeedFailure(feed, Exception()));

    expect(store.state.feeds[feed].status, Status.failure);
    expect(store.state.feeds[feed].exception, isNotNull);

    store.dispatch(LoadFeed(feed));

    expect(store.state.feeds[feed].status, Status.processing);
    expect(store.state.feeds[feed].exception, isNull);
  });

  test('LoadFeed should remove items and next when reset is true', () {
    final store = testStore();
    final feed = Feed(FeedType.home, FeedSort.best);
    final posts = Iterable.generate(5).map((_) => MockPost());

    store.dispatch(LoadFeed(feed));
    store.dispatch(LoadFeedSuccess(feed, posts, '123'));

    expect(store.state.feeds[feed], isNotNull);
    expect(store.state.feeds[feed].posts.length, 5);
    expect(store.state.feeds[feed].next, isNotNull);

    store.dispatch(LoadFeed(feed, reset: true));

    expect(store.state.feeds[feed].posts.length, 0);
    expect(store.state.feeds[feed].next, isNull);
  });

  test('LoadFeedSuccess should fill relevant fields', () {
    final store = testStore();
    final feed = Feed(FeedType.home, FeedSort.best);
    final posts = Iterable.generate(5).map((_) => MockPost());

    store.dispatch(LoadFeed(feed));
    store.dispatch(LoadFeedSuccess(feed, posts, '123'));

    final state = store.state.feeds[feed];
    expect(state, isNotNull);
    expect(state.status, Status.success);
    expect(state.exception, isNull);
    expect(state.posts.length, 5);
    expect(state.next, '123');
  });

  test('Sequential LoadFeedSuccess should add posts and update next', () {
    final store = testStore();
    final feed = Feed(FeedType.home, FeedSort.best);
    final posts = Iterable.generate(5).map((_) => MockPost());

    store.dispatch(LoadFeed(feed));
    store.dispatch(LoadFeedSuccess(feed, posts, '123'));

    expect(store.state.feeds[feed].posts.length, 5);
    expect(store.state.feeds[feed].next, '123');

    store.dispatch(LoadFeedSuccess(feed, posts, '456'));

    expect(store.state.feeds[feed].posts.length, 10);
    expect(store.state.feeds[feed].next, '456');
  });

  test('LoadFeedFailure should move FeedState to error state', () {
    final store = testStore();
    final feed = Feed(FeedType.home, FeedSort.best);
    final exception = Exception('test');

    store.dispatch(LoadFeed(feed));
    store.dispatch(LoadFeedFailure(feed, exception));

    expect(store.state.feeds[feed].status, Status.failure);
    expect(store.state.feeds[feed].exception, exception);
  });

  test('Dispose should remove FeedState from AppState', () {
    final store = testStore();
    final feed = Feed(FeedType.home, FeedSort.best);

    store.dispatch(LoadFeed(feed));

    expect(store.state.feeds[feed], isNotNull);

    store.dispatch(DisposeFeed(feed));

    expect(store.state.feeds[feed], isNull);
  });
}
