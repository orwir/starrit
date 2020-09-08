import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/app/action/startup.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';

/// Initial application screen.
///
/// Shows while waiting the app initialization.
class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        distinct: true,
        converter: _ViewModel.fromStore,
        onInitialBuild: (viewModel) => _onStateChanged(context, viewModel),
        onDidChange: (viewModel) => _onStateChanged(context, viewModel),
        builder: (_, viewModel) => _content(viewModel),
      );

  Widget _content(_ViewModel viewModel) {
    if (viewModel.failed) return _FailureView(viewModel);
    return Scaffold(body: Center(child: CircularProgressIndicator()));
  }

  void _onStateChanged(BuildContext context, _ViewModel viewModel) {
    if (viewModel.succeed) {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => FeedScreen(viewModel.lastFeed)),
      );
    }
  }
}

class _FailureView extends StatelessWidget {
  final _ViewModel viewModel;

  _FailureView(this.viewModel) : assert(viewModel != null);

  @override
  Widget build(BuildContext context) => Scaffold(
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(viewModel.errorMessage),
              SizedBox(height: 16),
              RaisedButton(
                child: Text('RETRY'),
                onPressed: viewModel.retry,
              ),
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

  /// True if status is [Status.failure].
  bool get failed => state.status == Status.failure;

  /// True if status is [Status.success].
  bool get succeed => state.status == Status.success;

  /// Returns error message if applicable.
  ///
  /// For debug mode returns exact message. For prod - placeholder.
  String get errorMessage => kDebugMode
      ? state.exception.toString()
      : 'Application initialization failed. Please try again.';

  /// Last visible feed.
  Feed get lastFeed => state.lastFeed;

  void retry() => store.dispatch(Startup());

  @override
  int get hashCode => hash([state.status]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is _ViewModel && state.status == other.state.status;
}
