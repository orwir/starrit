import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/navigation.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';
import 'package:starrit/settings/actions.dart';

/// Displays placeholder while app state intializing.
/// After AppState initialized prefetches data for the first FeedScreen
/// and then opens it.
class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        converter: _ViewModel.fromStore,
        onInitialBuild: _onStateUpdate,
        onDidChange: _onStateUpdate,
        builder: (context, viewModel) => viewModel.stateLoadFailed
            ? _buildFailureView(context, viewModel)
            : _buildLoadingView(context, viewModel),
      );

  Widget _buildFailureView(BuildContext context, _ViewModel viewModel) =>
      Scaffold(
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text('Something went wrong. Please try again.'),
              SizedBox(height: 16),
              RaisedButton(
                child: Text('Retry'),
                onPressed: viewModel.retry,
              ),
            ],
          ),
        ),
      );

  Widget _buildLoadingView(BuildContext context, _ViewModel viewModel) =>
      Scaffold(body: Center(child: CircularProgressIndicator()));

  void _onStateUpdate(_ViewModel viewModel) {
    if (viewModel.stateLoadSucceed) {
      if (viewModel.dataPrefetched) {
        viewModel.openFeedScreen();
      } else if (!viewModel.dataPrefetchStarted) {
        viewModel.prefetchFeedData();
      }
    }
  }
}

@immutable
class _ViewModel {
  static _ViewModel fromStore(Store<AppState> store) => _ViewModel(store);

  final Store<AppState> store;

  _ViewModel(this.store);

  /// Whether initial state loading failed.
  bool get stateLoadFailed => store.state.status == StateStatus.failure;

  /// Whether initial state loaded successfully.
  bool get stateLoadSucceed => store.state.status == StateStatus.success;

  /// Latest feed stored in the preferences.
  Feed get latestFeed => store.state.latestFeed;

  /// Whether data state for latest feed exists in the storage.
  bool get dataPrefetchStarted => store.state.feeds[latestFeed] != null;

  /// Whether data for Feed Screen loaded. No matter successfully or not.
  bool get dataPrefetched => [StateStatus.success, StateStatus.failure]
      .contains(store.state.feeds[latestFeed]?.status);

  /// Try to load initial state.
  void retry() {
    store.dispatch(LoadPreferences());
  }

  /// Request data prefetch.
  void prefetchFeedData() {
    store.dispatch(LoadFeedData(latestFeed));
  }

  /// Open Feed Screen and link it with prefetched data.
  void openFeedScreen() {
    navigatorStore.currentState.pushReplacement(
        MaterialPageRoute(builder: (context) => FeedScreen(latestFeed)));
  }
}
