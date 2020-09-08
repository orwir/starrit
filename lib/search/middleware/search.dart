import 'dart:async';

import 'package:redux/redux.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/network.dart';
import 'package:starrit/search/action/dispose.dart';
import 'package:starrit/search/action/load.dart';
import 'package:starrit/search/function/load.dart';

class SearchMiddleware extends MiddlewareClass<AppState> {
  final Network _network;
  final _delay = Duration(milliseconds: 500);
  Timer _debounce = Timer(Duration.zero, () {});

  SearchMiddleware(this._network);

  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is LoadSuggestions) {
      _debounce.cancel();
      _debounce = Timer(_delay, () => _load(store, action));
    }
    if (action is DisposeSearch) _debounce.cancel();
  }

  void _load(Store<AppState> store, LoadSuggestions action) async {
    try {
      final suggestions = await loadSuggestions(_network, action.query);
      store.dispatch(LoadSuggestionsSuccess(suggestions));
    } on Exception catch (e) {
      store.dispatch(LoadSuggestionsFailure(e));
    }
  }
}
