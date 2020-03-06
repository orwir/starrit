import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/actions/search.dart';
import 'package:starrit/models/feed.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/screens/feed/feed.dart';
import 'package:starrit/utils/object.dart';

class SearchScreen extends StatelessWidget {
  static const routeName = '/search';

  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      distinct: true,
      onDispose: (store) => store.dispatch(SearchDisposeAction()),
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

class _ViewModel {
  static _ViewModel fromStore(Store<AppState> store) =>
      _ViewModel(store.state.search, store.dispatch);

  _ViewModel(this._state, this._dispatcher);

  final SearchState _state;
  final Function _dispatcher;

  Sort get sort => _state.sort;
  set sort(Sort value) => _dispatcher(SortUpdateAction(value));
  Iterable<Type> get suggestions => _state.suggestions;

  void openFeed(BuildContext context, Type type) {
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
