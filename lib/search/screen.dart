import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/navigation.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';
import 'package:starrit/search/actions.dart';

class SearchScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        onDispose: (store) => store.dispatch(DisposeSearchData()),
        converter: _ViewModel.fromStore,
        builder: (context, viewModel) => Scaffold(
          appBar: AppBar(
            title: TextField(
              autofocus: true,
              textInputAction: TextInputAction.search,
              onChanged: viewModel.search,
            ),
            actions: [
              DropdownButton(
                value: viewModel.sort,
                onChanged: (sort) => viewModel.sort = sort,
                items: [
                  for (final sort in Sort.values)
                    DropdownMenuItem(child: Text(sort.label), value: sort)
                ],
              ),
            ],
          ),
          body: viewModel.loading
              ? LinearProgressIndicator()
              : ListView(
                  children: [
                    for (final type in viewModel.suggestions)
                      ListTile(
                        title: Text(type.label),
                        onTap: () =>
                            viewModel.openFeedScreen(type, viewModel.sort),
                      ),
                  ],
                ),
        ),
      );
}

class _ViewModel {
  static _ViewModel fromStore(Store<AppState> store) => _ViewModel(store);

  final Store<AppState> store;

  _ViewModel(this.store);

  Sort get sort => store.state.search.sort;
  set sort(Sort value) => store.dispatch(UpdateSort(value));
  List<Type> get suggestions => store.state.search.suggestions;
  bool get loading => store.state.search.status == StateStatus.processing;

  void search(String query) => store.dispatch(LoadSuggestions(query));

  void openFeedScreen(Type type, Sort sort) async {
    Nav.state.pushReplacement(
      MaterialPageRoute(builder: (context) => FeedScreen(type + sort)),
    );
  }
}
