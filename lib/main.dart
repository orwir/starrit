import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:redux/redux.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux_thunk/redux_thunk.dart';
import 'package:starrit/middlewares/logger.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/reducers/main.dart';
import 'package:starrit/screens/feed/feed.dart';
import 'package:starrit/theme/styles.dart';

main() {
  runApp(StarritApp());
}

class StarritApp extends StatelessWidget {
  final Store<AppState> store = Store(
    reducer,
    initialState: AppState.initial(),
    middleware: [thunkMiddleware, logger],
  );

  @override
  Widget build(BuildContext context) {
    return StoreProvider(
      store: store,
      child: MaterialApp(
        theme: lightTheme,
        darkTheme: darkTheme,
        home: FeedScreen(),
      ),
    );
  }
}
