import 'package:flutter/cupertino.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/function/preferences.dart';

void main() {
  setUpAll(() {
    WidgetsFlutterBinding.ensureInitialized();
    SharedPreferences.setMockInitialValues({});
  });

  test('loadPrefrences should load saved data', () async {
    final prefs1 = await loadPreferences();

    expect(prefs1.access, isNull);
    expect(prefs1.lastFeed, isNull);
    expect(prefs1.blurNsfw, isNull);

    await updateAccess(Access.revoked);
    await updateBlurNsfw(true);
    await updateLastFeed(Feed(FeedType.popular, FeedSort.hot));
    final prefs2 = await loadPreferences();

    expect(prefs2.access, Access.revoked);
    expect(prefs2.blurNsfw, true);
    expect(prefs2.lastFeed, isNotNull);
    expect(prefs2.lastFeed.type, FeedType.popular);
    expect(prefs2.lastFeed.sort, FeedSort.hot);
  });
}
