import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/middleware/feed.dart';
import 'package:starrit/model/state.dart';
import 'package:starrit/reducer/feed.dart';
import 'package:starrit/repository/feed.dart';
import 'package:starrit/screen/posts.dart';
import 'package:starrit/screen/splash.dart';

main() {
  runApp(StarritApp());
}

class StarritApp extends StatelessWidget {
  static final FeedRepository feedRepository = FeedRepository();

  final store = Store<AppState>(
    combineReducers(createFeedReducers()),
    initialState: AppState(),
    middleware: [...createFeedMiddlewares(feedRepository)],
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
