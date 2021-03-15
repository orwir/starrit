import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/service.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/model/preferences.dart';
import 'package:starrit/common/util/object.dart';

const _access = 'access';
const _lastFeed = 'lastFeed';
const _blurNsfw = 'blurNsfw';

mixin SettingsService on Service {
  /// Loads saved preferences.
  Future<Preferences> loadPreferences() async {
    return Preferences(
      access: sharedPreferences.getInt(_access)?.into((i) => Access.values[i]),
      lastFeed: Feed.fromJson(sharedPreferences.getString(_lastFeed)),
      blurNsfw: sharedPreferences.getBool(_blurNsfw),
    );
  }

  Future<void> updateBlurNsfw(bool enabled) async {
    await sharedPreferences.setBool(_blurNsfw, enabled);
  }

  Future<void> updateLastFeed(Feed feed) async {
    await sharedPreferences.setString(_lastFeed, feed.toJson());
  }

  Future<void> updateAccess(Access access) async {
    await sharedPreferences.setInt(_access, access.index);
  }
}
