import 'dart:async';

import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/util/error.dart';
import 'package:starrit/search/action/load.dart';
import 'package:starrit/search/service.dart';

class SearchMiddleware extends MiddlewareClass<AppState> {
  final SearchService _service;
  final _delay = Duration(milliseconds: 500);
  Timer _debounce = Timer(Duration.zero, () {});

  SearchMiddleware(this._service) : assert(_service != null);

  @override
  void call(Store<AppState> store, dynamic action, NextDispatcher next) {
    next(action);
    if (action is LoadSuggestions) {
      _debounce.cancel();
      _debounce = Timer(_delay, () => _load(store, action));
    }
  }

  void _load(Store<AppState> store, LoadSuggestions action) async {
    try {
      final suggestions = await _service.loadSuggestions(action.query);
      store.dispatch(LoadSuggestionsSuccess(suggestions));
    } catch (e) {
      store.dispatch(LoadSuggestionsFailure(normalize(e)));
    }
  }
}
