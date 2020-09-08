import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/model/preferences.dart';
import 'package:starrit/settings/pref.dart';

/// Load app preferences from a local storage.
Future<Preferences> loadPreferences() async {
  final prefs = await SharedPreferences.getInstance();
  return Preferences(
    access: prefs.getInt(Pref.access.key)?.into((i) => Access.values[i]),
    lastFeed: Feed.fromJson(prefs.getString(Pref.lastFeed.key)),
    blurNsfw: prefs.getBool(Pref.blurNsfw.key),
  );
}

/// Update blurNsfw preference in a local storage.
Future updateBlurNsfw(bool enabled) async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.setBool(Pref.blurNsfw.key, enabled);
}

/// Update lastFeed preference in a local storage.
Future updateLastFeed(Feed feed) async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.setString(Pref.lastFeed.key, feed.toJson());
}

/// Update access preference in a local storage.
Future updateAccess(Access access) async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.setInt(Pref.access.key, access.index);
}
