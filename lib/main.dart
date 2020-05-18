import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/navigation.dart';
import 'package:starrit/common/actions.dart';
import 'package:starrit/common/functions.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/redux.dart';
import 'package:starrit/splash/screen.dart';

main() => runApp(StarritApplication());

/// Starting point of the App.
class StarritApplication extends StatelessWidget {
  final store = Store<AppState>(
    appReducer,
    initialState: AppState.initial(),
    middleware: appMiddleware,
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
