import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:redux/redux.dart';
import 'package:redux_thunk/redux_thunk.dart';
import 'package:starrit/access/reducer.dart';
import 'package:starrit/app.dart';
import 'package:starrit/common/functions.dart';
import 'package:starrit/common/middlewares.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/reducer.dart';
import 'package:starrit/search/reducer.dart';
import 'package:starrit/settings/reducer.dart';
import 'package:starrit/splash/actions.dart';
import 'package:starrit/splash/reducer.dart';

void main() {
  final store = Store<AppState>(
    combineReducers([
      splashReducer,
      settingsReducer,
      accessReducer,
      feedReducer,
      searchReducer,
    ]),
    initialState: AppState.initial,
    middleware: [
      thunkMiddleware,
      /*
      EpicMiddleware(combineEpics([
        splashEpic,
        settingsEpic,
        accessEpic,
        feedEpic,
        searchEpic,
      ])),
      */
      if (kDebugMode)
        logger,
    ],
  );

  final links = BasicMessageChannel('starrit/links', StringCodec());

  runApp(StarritApplication(store));

  store.dispatch(InitApplication());
  links.setMessageHandler((link) async {
    handleLink(store, link);
    return null;
  });
}
