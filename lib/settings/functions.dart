import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/actions.dart';
import 'package:starrit/settings/model/pref.dart';

Future<dynamic> loadPreferences() async {
  try {
    final prefs = await SharedPreferences.getInstance();
    final blurNsfw = prefs.getBool(Pref.blurNsfw);
    final latestFeed = Feed.fromJson(prefs.getString(Pref.latestFeed));
    final access = prefs.getInt(Pref.access)?.into((i) => Access.values[i]);

    return LoadPreferencesSuccess(
      latestFeed: latestFeed,
      blurNsfw: blurNsfw,
      access: access,
    );
  } on Exception catch (e) {
    return LoadPreferencesFailure(e);
  }
}

Future<dynamic> updatePreference(UpdatePreference update) async {
  try {
    final prefs = await SharedPreferences.getInstance();
    if (update.blurNsfw != null) {
      await prefs.setBool(Pref.blurNsfw, update.blurNsfw);
    }
    if (update.latestFeed != null) {
      await prefs.setString(Pref.latestFeed, update.latestFeed.toJson());
    }

    return UpdatePreferenceSuccess(
      latestFeed: update.latestFeed,
      blurNsfw: update.blurNsfw,
    );
  } on Exception catch (e) {
    return UpdatePreferenceFailure(e);
  }
}
