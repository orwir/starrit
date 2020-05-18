import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/common/actions.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/common/navigation.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';

class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        converter: _ViewModel.fromStore,
        onInitialBuild: _onStateUpdate,
        onDidChange: _onStateUpdate,
        builder: (context, viewModel) => viewModel.initStateFailure
            ? _buildFailureView(context, viewModel)
            : _buildLoadingView(context, viewModel),
      );

  Widget _buildFailureView(BuildContext context, _ViewModel viewModel) =>
      Scaffold(
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(viewModel.errorMessage),
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
    if (viewModel.initStateSuccess) viewModel.openFeedScreen();
  }
}

@immutable
class _ViewModel {
  static _ViewModel fromStore(Store<AppState> store) => _ViewModel(store);

  final Store<AppState> store;

  _ViewModel(this.store);

  bool get initStateSuccess => store.state?.status == StateStatus.success;

  bool get initStateFailure => store.state?.status == StateStatus.failure;

  Feed get latestFeed => store.state.latestFeed;

  String get errorMessage => store.state.exception?.toString() ?? '';

  void retry() => store.dispatch(InitApplication());

  void openFeedScreen() {
    Nav.state.pushReplacement(
      MaterialPageRoute(builder: (context) => FeedScreen(latestFeed)),
    );
  }
}
