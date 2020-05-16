import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/common/navigation.dart';
import 'package:starrit/common/redux.dart';
import 'package:starrit/settings/actions.dart';
import 'package:starrit/splash/screen.dart';

main() => runApp(StarritApplication());

/// Starting point of the App.
class StarritApplication extends StatelessWidget {
  final Store store = Store<AppState>(
    appReducer,
    initialState: AppState.initial(),
    middleware: appMiddleware,
  );

  StarritApplication() {
    if (store.state.status == StateStatus.initial) {
      store.dispatch(LoadPreferences());
    }
  }

  @override
  Widget build(BuildContext context) => StoreProvider<AppState>(
        store: store,
        child: MaterialApp(
          home: SplashScreen(),
          navigatorKey: navigatorStore,
        ),
      );
}
