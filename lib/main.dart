import 'package:flutter/cupertino.dart';
import 'package:redux/redux.dart';
import 'package:starrit/app/action/startup.dart';
import 'package:starrit/app/app.dart';
import 'package:starrit/app/middleware/logger.dart';
import 'package:starrit/app/middleware/startup.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/network.dart';
import 'package:starrit/feed/middleware/feed.dart';
import 'package:starrit/reducer.dart';
import 'package:starrit/search/middleware/search.dart';
import 'package:starrit/settings/middleware/settings.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  Store<AppState> store;
  final network = Network(() => store);
  store = Store<AppState>(
    starritReducer,
    initialState: AppState.initial,
    middleware: [
      StartupMiddleware(network),
      SettingsMiddleware(),
      FeedMiddleware(network),
      SearchMiddleware(network),
      LoggerMiddleware(),
    ],
  );

  store.dispatch(Startup());
  runApp(StarritApplication(store));
}
