import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/app/action/startup.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';

import '../helper.dart';

void main() {
  test('Startup should reset state to AppState.initial', () {
    final store = testStore();

    store.dispatch(StartupFailure(Exception()));

    expect(store.state, isNot(AppState.initial));

    store.dispatch(Startup());

    expect(store.state, AppState.initial);
  });

  test('StartupSuccess should update relevant fields', () {
    final store = testStore();
    final feed = Feed(FeedType.popular, FeedSort.controversial);
    final action = StartupSuccess(
      access: Access.authorized,
      feed: feed,
      blurNsfw: true,
      posts: [MockPost(), MockPost()],
      next: '123',
    );

    store.dispatch(action);

    expect(store.state.status, Status.success);
    expect(store.state.access, action.access);
    expect(store.state.lastFeed, action.feed);
    expect(store.state.blurNsfw, action.blurNsfw);
    expect(store.state.feeds, isNotNull);
    expect(store.state.feeds[feed], isNotNull);
    expect(store.state.feeds[feed].status, Status.success);
    expect(store.state.feeds[feed].posts.length, 2);
    expect(store.state.feeds[feed].next, '123');
  });

  test('StartupFailure should update relevant fields', () {
    final store = testStore();
    final action = StartupFailure(Exception());

    store.dispatch(action);

    expect(store.state.status, Status.failure);
    expect(store.state.exception, action.exception);
  });
}
