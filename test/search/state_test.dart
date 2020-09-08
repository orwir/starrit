import 'package:built_collection/built_collection.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/app/action/startup.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/search/action/dispose.dart';
import 'package:starrit/search/action/load.dart';
import 'package:starrit/search/action/sort.dart';

import '../helper.dart';

void main() {
  test('Initial suggestions list should contains FeedType.values', () {
    final store = testStore();

    expect(store.state.searchSuggestions, BuiltList<FeedType>(FeedType.values));
  });

  test(
    'LoadSuggestions should set status to "processing" and reset exception when dispatched',
    () {
      final store = testStore();

      store.dispatch(StartupFailure(Exception()));

      expect(store.state.status, isNot(Status.processing));
      expect(store.state.exception, isNotNull);

      store.dispatch(LoadSuggestions('query'));

      expect(store.state.status, Status.processing);
      expect(store.state.exception, isNull);
    },
  );

  test(
    'LoadSuggestionsSuccess should set status to "success", exception to fail and fill suggestions',
    () {
      final store = testStore();
      final suggestions = [
        FeedType.subreddit('1'),
        FeedType.subreddit('2'),
        FeedType.subreddit('3'),
      ];

      expect(store.state.searchSuggestions,
          isNot(BuiltList<FeedType>(suggestions)));

      store.dispatch(LoadSuggestionsSuccess(suggestions));

      expect(store.state.searchSuggestions, BuiltList<FeedType>(suggestions));
    },
  );

  test(
    'LoadSuggestionsFailure should set status to "failure" and update exception',
    () {
      final store = testStore();

      expect(store.state.status, isNot(Status.failure));
      expect(store.state.exception, isNull);

      store.dispatch(LoadSuggestionsFailure(Exception()));

      expect(store.state.status, Status.failure);
      expect(store.state.exception, isNotNull);
    },
  );

  test('UpdateSort should change sorting order', () {
    final store = testStore();

    expect(store.state.searchSort, isNot(FeedSort.controversial));

    store.dispatch(UpdateSort(FeedSort.controversial));

    expect(store.state.searchSort, FeedSort.controversial);
  });

  test(
    'DisposeSearch should reset suggestions and sort order to default values',
    () {
      final store = testStore();

      store.dispatch(LoadSuggestionsSuccess([]));
      store.dispatch(UpdateSort(FeedSort.hot));

      expect(
        store.state.searchSuggestions,
        isNot(AppState.initial.searchSuggestions),
      );
      expect(store.state.searchSort, isNot(AppState.initial.searchSort));

      store.dispatch(DisposeSearch());

      expect(store.state.searchSuggestions, AppState.initial.searchSuggestions);
      expect(store.state.searchSort, AppState.initial.searchSort);
    },
  );
}
