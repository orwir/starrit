import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/splash/screen.dart';

/// Top-level widget.
class StarritApplication extends StatelessWidget {
  final Store<AppState> store;

  StarritApplication(this.store);

  @override
  Widget build(BuildContext context) => StoreProvider<AppState>(
        store: store,
        child: MaterialApp(
          title: 'Stare It!',
          theme: ThemeData.light(),
          darkTheme: ThemeData.dark(),
          home: SplashScreen(),
        ),
      );
}
