import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';
import 'package:starrit/search/action/dispose.dart';
import 'package:starrit/search/action/load.dart';
import 'package:starrit/search/action/sort.dart';

class SearchScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        onDispose: (store) => store.dispatch(DisposeSearch()),
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
                onChanged: viewModel.changeSort,
                items: [
                  for (final sort in FeedSort.values)
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
                        onTap: () => viewModel.openFeedScreen(
                          context,
                          type,
                          viewModel.sort,
                        ),
                      )
                  ],
                ),
        ),
      );
}

class _ViewModel {
  static _ViewModel fromStore(Store<AppState> store) => _ViewModel(store);

  final Store<AppState> store;
  final AppState state;

  _ViewModel(this.store)
      : assert(store != null),
        assert(store.state != null),
        state = store.state;

  FeedSort get sort => state.searchSort;

  Iterable<FeedType> get suggestions => state.searchSuggestions;

  bool get loading => state.status == Status.processing;

  void changeSort(FeedSort value) => store.dispatch(UpdateSort(value));

  void search(String query) => store.dispatch(LoadSuggestions(query));

  void openFeedScreen(BuildContext context, FeedType type, FeedSort sort) {
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => FeedScreen(Feed(type, sort))),
    );
  }
}
