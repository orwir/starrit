import 'package:redux/redux.dart';
import 'package:redux_thunk/redux_thunk.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/actions.dart';
import 'package:starrit/settings/functions.dart';

ThunkAction<AppState> updateBlurNsfw(bool value) {
  return (Store<AppState> store) async {
    await saveBlurNsfw(value);
    store.dispatch(UpdateBlurNsfw(value));
  };
}

ThunkAction<AppState> updateLatestFeed(Feed feed) {
  return (Store<AppState> store) async {
    await saveLatestFeed(feed);
    store.dispatch(UpdateLatestFeed(feed));
  };
}
