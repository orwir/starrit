import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:redux/redux.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/access/epic.dart';
import 'package:starrit/access/reducer.dart';
import 'package:starrit/common/functions.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/navigation.dart';
import 'package:starrit/feed/epic.dart';
import 'package:starrit/feed/reducer.dart';
import 'package:starrit/search/epic.dart';
import 'package:starrit/search/reducer.dart';
import 'package:starrit/settings/epic.dart';
import 'package:starrit/settings/reducer.dart';
import 'package:starrit/splash/actions.dart';
import 'package:starrit/splash/epic.dart';
import 'package:starrit/splash/reducer.dart';
import 'package:starrit/splash/screen.dart';

main() => runApp(StarritApplication());

/// Starting point of the App.
class StarritApplication extends StatelessWidget {
  final store = Store<AppState>(
    combineReducers([
      splashReducer,
      settingsReducer,
      accessReducer,
      feedReducer,
      searchReducer,
    ]),
    initialState: AppState.initial,
    middleware: [
      EpicMiddleware(combineEpics([
        splashEpic,
        settingsEpic,
        accessEpic,
        feedEpic,
        searchEpic,
      ])),
      logger,
    ],
  );

  final links = BasicMessageChannel('starrit/links', StringCodec());

  StarritApplication() {
    WidgetsFlutterBinding.ensureInitialized();
    WidgetsBinding.instance.addPostFrameCallback((_) => _init());
  }

  @override
  Widget build(BuildContext context) => StoreProvider<AppState>(
        store: store,
        child: MaterialApp(
          home: SplashScreen(),
          navigatorKey: Nav.key,
        ),
      );

  void _init() {
    links.setMessageHandler((link) async {
      handleLink(link);
      return null;
    });
    store.dispatch(InitApplication());
  }
}
