import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/splash/action/startup.dart';
import 'package:starrit/app/app.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';

import '../helper.dart';

void main() {
  testWidgets(
    'SplashScreen should render CircularProgressIndicator when app starts.',
    (WidgetTester tester) async {
      await tester.pumpWidget(StarritApplication(testStore()));

      expect(find.byType(CircularProgressIndicator), findsOneWidget);
    },
  );

  testWidgets(
    'SplashScreen should render error view when dispatched StartupFailure.',
    (WidgetTester tester) async {
      final store = testStore();

      store.dispatch(StartupFailure(Exception('Test exception')));

      expect(store.state.status, Status.failure);
      expect(store.state.exception, isNotNull);

      await tester.pumpWidget(StarritApplication(store));

      expect(find.text('Exception: Test exception'), findsOneWidget);
      expect(find.byType(ElevatedButton), findsOneWidget);
    },
  );

  testWidgets(
    'SplashScreen should navigate to FeedScreen when dispatched StatupSuccess.',
    (WidgetTester tester) async {
      final store = testStore();

      store.dispatch(StartupSuccess(
        access: Access.unspecified,
        feed: Feed(FeedType.home, FeedSort.best),
        blurNsfw: false,
        posts: [],
      ));

      expect(store.state.status, Status.success);

      await tester.pumpWidget(StarritApplication(store));
      await tester.pump();
      await tester.pump();

      expect(find.byType(FeedScreen), findsOneWidget);
    },
  );
}
