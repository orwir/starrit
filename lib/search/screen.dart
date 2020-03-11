import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/feed/models/feed.dart';
import 'package:starrit/feed/screen.dart';
import 'package:starrit/common/models/state.dart';
import 'package:starrit/common/utils/object.dart';

import 'actions.dart';

class SearchScreen extends StatelessWidget {
  static const routeName = '/search';

  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      distinct: true,
      onInit: (store) {
        store.dispatch(SearchSuggestionsRequestAction(''));
      },
      converter: _ViewModel.fromStore,
      builder: (context, viewModel) => Scaffold(
        appBar: AppBar(
          title: TextField(
            autofocus: false,
            enabled: false,
            textInputAction: TextInputAction.search,
          ),
          actions: <Widget>[
            DropdownButton<Sort>(
              value: viewModel.sort,
              items: Sort.values
                  .map((sort) => DropdownMenuItem(
                        child: Text(sort.label),
                        value: sort,
                      ))
                  .toList(),
              onChanged: (sort) => viewModel.sort = sort,
            ),
          ],
        ),
        body: ListView(
          children: <Widget>[
            ...viewModel.suggestions.map((type) => ListTile(
                  title: Text(type.label),
                  onTap: () => viewModel.openFeed(context, type),
                ))
          ],
        ),
      ),
    );
  }
}

@immutable
class _ViewModel {
  static _ViewModel fromStore(Store<AppState> store) =>
      _ViewModel(store.state.search, store.dispatch);

  _ViewModel(this._state, this._dispatch);

  final SearchState _state;
  final Function _dispatch;

  Sort get sort => _state.sort;
  set sort(Sort value) => _dispatch(SearchSortChangeAction(value));
  Iterable<Type> get suggestions => _state.suggestions;

  void openFeed(BuildContext context, Type type) {
    _dispatch(SearchDisposeAction());
    Navigator.pushReplacementNamed(
      context,
      FeedScreen.routeName,
      arguments: type + sort,
    );
  }

  @override
  int get hashCode => hash([_state]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is _ViewModel &&
          runtimeType == other.runtimeType &&
          _state == other._state;
}
