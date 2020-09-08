import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/widget/banner.dart';
import 'package:starrit/app/action/startup.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/env.dart';
import 'package:starrit/feed/action/load.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';
import 'package:starrit/feed/widget/post/post.dart';
import 'package:starrit/search/screen.dart';
import 'package:visibility_detector/visibility_detector.dart';

import '../helper.dart';

void main() {
  setUpAll(() {
    VisibilityDetectorController.instance.updateInterval = Duration.zero;
  });

  tearDown(() {
    TestEnv.clientID = null;
  });

  group('FeedScreen', () {
    testWidgets(
      'should request FeedState when FeedState does not exist.',
      (WidgetTester tester) async {
        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        expect(store.state.feeds[feed], isNull);

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));
        await tester.pump();

        expect(store.state.feeds[feed], isNotNull);
        expect(store.state.feeds[feed].status, Status.processing);

        expect(find.byType(LinearProgressIndicator), findsOneWidget);
      },
    );

    testWidgets(
      'should show FailureView when request failed.',
      (WidgetTester tester) async {
        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));
        store.dispatch(LoadFeedFailure(feed, Exception('test')));
        await tester.pump();

        expect(find.text('Exception: test'), findsOneWidget);
      },
    );

    testWidgets(
      'should request new data when retry pressed.',
      (WidgetTester tester) async {
        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));
        store.dispatch(LoadFeedFailure(feed, Exception('test')));
        await tester.pump();
        await tester.tap(find.text('RETRY'));
        await tester.pump();

        expect(find.text('RETRY'), findsNothing);
        expect(find.byType(LinearProgressIndicator), findsOneWidget);
      },
    );

    testWidgets(
      'should show AccessBanner when build capable and user did not make a decision.',
      (WidgetTester tester) async {
        TestEnv.clientID = '123';

        expect(Env.supportAuthorization, true);

        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        store.dispatch(LoadFeed(feed));
        store.dispatch(LoadFeedSuccess(feed, [], null));

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));

        expect(store.state.feeds[feed].status, Status.success);
        expect(store.state.access.stable, false);
        expect(find.byType(AccessBanner), findsOneWidget);
      },
    );

    testWidgets(
      'should not show AccessBanner when build is not capable.',
      (WidgetTester tester) async {
        expect(Env.supportAuthorization, false);

        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        store.dispatch(LoadFeed(feed));
        store.dispatch(LoadFeedSuccess(feed, [], null));

        expect(store.state.feeds[feed].status, Status.success);

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));

        expect(store.state.feeds[feed].status, Status.success);
        expect(find.byType(AccessBanner), findsNothing);
      },
    );

    testWidgets(
      'should not show AccessBanner when user already made a decision.',
      (WidgetTester tester) async {
        TestEnv.clientID = '123';

        expect(Env.supportAuthorization, true);

        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        store.dispatch(StartupSuccess(
          access: Access.authorized,
          feed: feed,
          blurNsfw: false,
          posts: [],
        ));

        expect(store.state.access.stable, true);

        store.dispatch(LoadFeed(feed));
        store.dispatch(LoadFeedSuccess(feed, [], null));

        expect(store.state.feeds[feed].status, Status.success);

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));

        expect(find.byType(AccessBanner), findsNothing);
      },
    );

    testWidgets(
      'should request new chunk of data if less than 10 elements unseen.',
      (WidgetTester tester) async {
        // TODO: should request new chunk of data if less than 10 elements unseen.
      },
      skip: true,
    );

    testWidgets(
      'should not request new chunk of data if 10 or more elements unseen.',
      (WidgetTester tester) async {
        // TODO: should not request new chunk of data if 10 or more elements unseen.
      },
      skip: true,
    );

    testWidgets(
      'should update last feed when FeedScreen is open.',
      (WidgetTester tester) async {
        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        store.dispatch(StartupSuccess(
          access: Access.unspecified,
          feed: Feed(FeedType.all, FeedSort.controversial),
          blurNsfw: false,
          posts: [],
        ));
        store.dispatch(LoadFeed(feed));
        store.dispatch(LoadFeedSuccess(feed, [], null));

        expect(store.state.lastFeed, isNot(feed));

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));

        expect(store.state.lastFeed, feed);
      },
    );

    testWidgets(
      'should reset feed data and start processting when reload button pressed.',
      (WidgetTester tester) async {
        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);
        store.dispatch(LoadFeed(feed));
        store.dispatch(LoadFeedSuccess(
          feed,
          Iterable.generate(50).map((_) => MockPost()),
          null,
        ));

        expect(store.state.feeds[feed].posts.length, 50);

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));

        expect(find.byType(PostView), findsWidgets);

        await tester.tap(find.byIcon(Icons.arrow_upward));
        await tester.pump();
        final state = store.state.feeds[feed];

        expect(state.status, Status.processing);
        expect(state.posts.length, 0);
        expect(state.next, isNull);
        expect(find.byType(PostView), findsNothing);
      },
    );

    testWidgets(
      'should toggle BlurNSFW flag when toggle button pressed.',
      (WidgetTester tester) async {
        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        store.dispatch(StartupSuccess(
          access: Access.anonymous,
          feed: feed,
          blurNsfw: false,
          posts: [],
        ));
        store.dispatch(LoadFeed(feed));
        store.dispatch(LoadFeedSuccess(feed, [], null));

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));
        await tester.tap(find.byIcon(Icons.visibility));

        expect(store.state.blurNsfw, true);

        await tester.pump();
        await tester.tap(find.byIcon(Icons.visibility_off));

        expect(store.state.blurNsfw, false);
      },
    );

    testWidgets(
      'should open SearchScreen when search button pressed.',
      (WidgetTester tester) async {
        final store = testStore();
        final feed = Feed(FeedType.home, FeedSort.best);

        store.dispatch(LoadFeed(feed));
        store.dispatch(LoadFeedSuccess(feed, [], null));

        await tester.pumpWidget(testWrapper(store, FeedScreen(feed)));
        await tester.tap(find.byIcon(Icons.search));
        await tester.pump();
        await tester.pump();

        expect(find.byType(SearchScreen), findsOneWidget);
      },
    );
  });
}
