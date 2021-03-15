import 'package:flutter/cupertino.dart';
import 'package:redux/redux.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/app/app.dart';
import 'package:starrit/app/logger.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/network.dart';
import 'package:starrit/common/service.dart';
import 'package:starrit/feed/middleware.dart';
import 'package:starrit/reducer.dart';
import 'package:starrit/search/middleware.dart';
import 'package:starrit/settings/middleware.dart';
import 'package:starrit/splash/action/startup.dart';
import 'package:starrit/splash/middleware.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  Store<AppState> store;
  final network = Network(() => store);
  final prefs = await SharedPreferences.getInstance();
  final service = StarritService(prefs, network);

  store = Store<AppState>(
    starritReducer,
    initialState: AppState.initial,
    middleware: [
      SplashMiddleware(service),
      SettingsMiddleware(service),
      FeedMiddleware(service),
      SearchMiddleware(service),
      LoggerMiddleware(),
    ],
  );

  store.dispatch(Startup());
  runApp(StarritApplication(store));
}
