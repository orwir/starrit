import 'package:flutter/foundation.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/actions.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/functions.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/settings/actions.dart';
import 'package:starrit/settings/functions.dart';

/// Simple logger to track dispatched actions and state updates (debug only).
void logger(Store<AppState> store, dynamic action, NextDispatcher next) {
  next(action);
  if (kDebugMode) print('> $action\n${store.state}');
}

void handleLink(String link) {
  print(link); // TODO: implement
}

Future<dynamic> initApplication(AppState zero) async {
  try {
    final LoadPreferencesSuccess prefs = await loadPreferences();
    final access = prefs.access ?? zero.access;
    final blurNsfw = prefs.blurNsfw ?? zero.blurNsfw;
    final latestFeed = prefs.latestFeed ?? zero.latestFeed;
    // TODO: init AuthState
    final LoadFeedDataSuccess feedData = await loadFeedData(
      LoadFeedData(latestFeed),
      access,
    );
    return InitApplicationSuccess(
      access: access,
      auth: null,
      blurNsfw: blurNsfw,
      feed: latestFeed,
      feedState: FeedState(
        feed: feedData.feed,
        status: StateStatus.success,
        posts: feedData.posts,
        next: feedData.next,
      ),
    );
  } on Exception catch (e) {
    return InitApplicationFailure(e);
  }
}
