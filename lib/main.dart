import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:redux/redux.dart';
import 'package:flutter_redux/flutter_redux.dart';

import 'feed/screen.dart';
import 'models/state.dart';
import 'redux.dart';
import 'styles.dart';
import 'navigation.dart';

main() => runApp(StarritApp());

class StarritApp extends StatelessWidget {
  final Store<AppState> store = Store(
    reducer,
    initialState: AppState(
      blurNsfw: false,
      search: SearchState.initial,
      feeds: {},
    ),
    middleware: middleware,
  );

  @override
  Widget build(BuildContext context) {
    return StoreProvider(
      store: store,
      child: MaterialApp(
        theme: lightTheme,
        darkTheme: darkTheme,
        initialRoute: FeedScreen.routeName,
        routes: routes,
      ),
    );
  }
}
