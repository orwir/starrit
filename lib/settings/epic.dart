import 'dart:convert';

import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/actions.dart';

const _keyBlurNsfw = 'blur_nsfw';
const _keyLatestFeed = 'latest_feed';

final Epic<AppState> settingsEpic = combineEpics([
  _loadPreferences,
  TypedEpic<AppState, UpdateBlurNsfw>(_updateBlurNsfw),
  TypedEpic<AppState, UpdateLatestFeed>(_updateLatestFeed),
]);

Stream<dynamic> _loadPreferences(
  Stream<dynamic> actions,
  EpicStore<AppState> store,
) {
  Future<dynamic> fetch() async {
    final prefs = await SharedPreferences.getInstance();
    final blurNsfw = prefs.getBool(_keyBlurNsfw);
    final latestFeed =
        Feed.fromJson(jsonDecode(prefs.getString(_keyLatestFeed) ?? '{}'));

    return LoadPreferencesSuccess(
      latestFeed: latestFeed ?? Type.home + Sort.best,
      blurNsfw: blurNsfw ?? false,
    );
  }

  final dispose = actions.whereType<LoadPreferences>();

  return actions
      .whereType<LoadPreferences>()
      .switchMap((_) => fetch().asStream().takeUntil(dispose));
}

Stream<dynamic> _updateBlurNsfw(
  Stream<UpdateBlurNsfw> actions,
  EpicStore<AppState> store,
) {
  Future<PreferencesUpdateSuccess> update(UpdateBlurNsfw update) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setBool(_keyBlurNsfw, update.blurNsfw);
    return PreferencesUpdateSuccess();
  }

  return actions.switchMap((action) => update(action).asStream());
}

Stream<dynamic> _updateLatestFeed(
  Stream<UpdateLatestFeed> actions,
  EpicStore<AppState> store,
) {
  Future<PreferencesUpdateSuccess> update(UpdateLatestFeed update) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_keyLatestFeed, update.feed.toJson());
    return PreferencesUpdateSuccess();
  }

  return actions.switchMap((action) => update(action).asStream());
}
