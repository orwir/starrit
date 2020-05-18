import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/splash/actions.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/functions.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/settings/actions.dart';
import 'package:starrit/settings/functions.dart';

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
