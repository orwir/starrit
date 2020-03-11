import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:redux/redux.dart';
import 'package:flutter_redux/flutter_redux.dart';

import 'common/models/state.dart';
import 'common/redux.dart';
import 'common/styles.dart';
import 'common/navigation.dart';
import 'feed/screen.dart';

main() => runApp(StarritApp());

class StarritApp extends StatelessWidget {
  final Store<AppState> store = Store(
    reducer,
    initialState: AppState(
      blurNsfw: false,
      search: SearchState.none,
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
