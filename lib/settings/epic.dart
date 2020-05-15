import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/settings/actions.dart';

final Epic<AppState> settingsEpic = combineEpics([
  _loadPreferences,
]);

Stream<dynamic> _loadPreferences(
  Stream<dynamic> actions,
  EpicStore<AppState> store,
) {
  Future<dynamic> fetch() async {
    // TODO: implement loading prefrences
    return LoadPreferencesSuccess(
      latestFeed: Type.home + Sort.best,
      blurNsfw: false,
    );
  }

  final dispose = actions.whereType<LoadPreferences>();

  return actions
      .whereType<LoadPreferences>()
      .switchMap((_) => fetch().asStream().takeUntil(dispose));
}
