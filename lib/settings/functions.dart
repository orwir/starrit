import 'dart:async';

import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/key.dart';
import 'package:starrit/settings/model/preferences.dart';

/// Load saved data from [SharedPreferences].
Future<Preferences> loadPreferences() async {
  final prefs = await SharedPreferences.getInstance();
  final blurNsfw = prefs.getBool(Pref.blurNsfw);
  final latestFeed = Feed.fromJson(prefs.getString(Pref.latestFeed));
  final access = prefs.getInt(Pref.access)?.into((i) => Access.values[i]);

  return Preferences(
    latestFeed: latestFeed,
    blurNsfw: blurNsfw,
    access: access,
  );
}

/// Save new value into [SharedPreferences].
Future<void> saveBlurNsfw(bool blurNsfw) async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.setBool(Pref.blurNsfw, blurNsfw);
}

/// Save new value into [SharedPreferences].
Future<void> saveLatestFeed(Feed feed) async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.setString(Pref.latestFeed, feed.toJson());
}

/// Save new value into [SharedPreferences].
Future<void> saveAccess(Access access) async {
  final prefs = await SharedPreferences.getInstance();
  await prefs.setInt(Pref.access, access.index);
}
