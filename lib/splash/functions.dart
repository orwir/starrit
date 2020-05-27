import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/functions.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/settings/functions.dart';
import 'package:starrit/splash/model/initial.dart';

Future<Initial> loadInitialState() async {
  final prefs = await loadPreferences();
  final access = prefs.access ?? AppState.initial.access;
  final blurNsfw = prefs.blurNsfw ?? AppState.initial.blurNsfw;
  final feed = prefs.latestFeed ?? AppState.initial.latestFeed;
  if (access == Access.authorized) {
    // TODO: obtain token
  }
  final chunk = await loadFeedChunk(feed, access, token: null);
  return Initial(
    access: access,
    auth: null,
    blurNsfw: blurNsfw,
    feed: FeedState(
      feed: feed,
      status: StateStatus.success,
      posts: chunk.posts,
      next: chunk.next,
    ),
  );
}
