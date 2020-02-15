import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/middleware/main.dart';
import 'package:starrit/model/state.dart';
import 'package:starrit/reducer/main.dart';
import 'package:starrit/screen/posts.dart';
import 'package:starrit/screen/splash.dart';

main() {
  runApp(StarritApp());
}

class StarritApp extends StatelessWidget {
  final store = Store<AppState>(
    reducer,
    initialState: AppState(),
    middleware: [fetchLatestFeed],
  );

  @override
  Widget build(BuildContext context) {
    return StoreProvider(
      store: store,
      child: MaterialApp(
        theme: ThemeData.light(),
        darkTheme: ThemeData.dark(),
        routes: <String, WidgetBuilder>{
          '/splash': (context) => SplashScreen(),
          '/posts': (context) => PostsScreen()
        },
        initialRoute: '/splash',
      ),
    );
  }
}
