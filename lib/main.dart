import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:redux/redux.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux_thunk/redux_thunk.dart';
import 'package:starrit/middleware/main.dart';
import 'package:starrit/model/state.dart';
import 'package:starrit/reducer/main.dart';
import 'package:starrit/screen/feed.dart';

import 'i18n/localization.dart';

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
        theme: ThemeData.light(),
        darkTheme: ThemeData.dark(),
        localizationsDelegates: [
          StarritLocalizations.delegate,
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
        ],
        supportedLocales: StarritLocalizations.supported,
        localeListResolutionCallback: _resolveLocale,
        home: FeedScreen(),
      ),
    );
  }

  Locale _resolveLocale(List<Locale> locales, Iterable<Locale> supported) {
    return locales.firstWhere(
      (locale) => supported.contains(locale),
      orElse: () => supported.first,
    );
  }
}
