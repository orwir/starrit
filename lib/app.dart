import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/navigation.dart';
import 'package:starrit/splash/screen.dart';

class StarritApplication extends StatelessWidget {
  final Store<AppState> store;

  StarritApplication(this.store);

  @override
  Widget build(BuildContext context) => StoreProvider<AppState>(
        store: store,
        child: MaterialApp(
          home: SplashScreen(),
          navigatorKey: Nav.key,
        ),
      );
}
