import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/splash/action/startup.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/common/view_model.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/screen.dart';

/// Initial application screen.
///
/// Shows progress bar while the application is setting up. If startup
/// failed shows an error with an ability to retry.
class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) => StoreConnector<AppState, _ViewModel>(
        distinct: true,
        converter: _ViewModel.fromStore,
        onInitialBuild: (viewModel) => _onStateChanged(context, viewModel),
        onDidChange: (viewModel) => _onStateChanged(context, viewModel),
        builder: (_, viewModel) => _body(viewModel),
      );

  Widget _body(_ViewModel viewModel) {
    if (viewModel.failure) return _FailureView(viewModel);
    return Scaffold(body: Center(child: CircularProgressIndicator()));
  }

  void _onStateChanged(BuildContext context, _ViewModel viewModel) {
    if (viewModel.success) {
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
              ElevatedButton(
                child: Text('RETRY'),
                onPressed: viewModel.retry,
              ),
            ],
          ),
        ),
      );
}

class _ViewModel extends ViewModel {
  static _ViewModel fromStore(Store<AppState> store) => _ViewModel(store);

  _ViewModel(Store<AppState> store) : super(store);

  /// Returns an error message if [failure] is true.
  ///
  /// If the application is not in debug mode returns a placeholder message.
  String get errorMessage => failure
      ? kDebugMode
          ? state.exception.toString()
          : 'Application initialization failed. Please try again.'
      : '';

  /// Last visible feed.
  Feed get lastFeed => state.lastFeed;

  /// Starts initialization process again.
  void retry() => store.dispatch(Startup());

  @override
  int get hashCode => hash([state.status]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is _ViewModel && state.status == other.state.status;
}
