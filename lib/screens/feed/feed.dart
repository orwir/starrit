import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/actions/feed.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/screens/feed/components/post/main.dart';

class FeedScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      onInit: (store) {
        if (!store.state.loading && store.state.posts.isEmpty) {
          store.dispatch(loadPosts(store.state.feed));
        }
      },
      converter: _ViewModel.fromStore,
      builder: (context, viewModel) => Scaffold(
        appBar: AppBar(
          title: Text(viewModel.title),
          actions: <Widget>[
            IconButton(icon: Icon(Icons.search), onPressed: null),
            IconButton(
              icon: Icon(
                viewModel.blurNsfw ? Icons.visibility_off : Icons.visibility,
              ),
              onPressed: viewModel.toggleBlurNsfw,
            ),
          ],
        ),
        body: ListView.separated(
          itemCount: viewModel.postsCount + (viewModel.showFooter ? 1 : 0),
          separatorBuilder: (context, index) => Divider(height: 1),
          itemBuilder: (context, index) {
            if (viewModel.shouldLoadMore(index)) viewModel.loadMore();

            if (index < viewModel.postsCount) {
              return PostView(viewModel.posts[index]);
            }
            if (viewModel.error != null) {
              return _buildFooter(viewModel);
            }
            return LinearProgressIndicator();
          },
        ),
      ),
    );
  }

  Widget _buildFooter(_ViewModel viewModel) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 16),
      child: Row(
        children: <Widget>[
          Expanded(
            child: Text(
              viewModel.errorMessage,
              overflow: TextOverflow.ellipsis,
            ),
          ),
          FlatButton(
            onPressed: () => viewModel.loadMore(),
            textTheme: ButtonTextTheme.primary,
            child: Text('RETRY'),
          ),
        ],
      ),
    );
  }
}

@immutable
class _ViewModel {
  final AppState _state;
  final Function _dispatch;
  final int _loadThreshold = 10;

  _ViewModel._(this._state, this._dispatch);

  static _ViewModel fromStore(Store<AppState> store) {
    return _ViewModel._(
      store.state,
      (dynamic action) => store.dispatch(action),
    );
  }

  String get title => _state.feed.asTitle;
  bool get loading => _state.loading;
  List<Post> get posts => _state.posts;
  int get postsCount => _state.posts.length;
  Post get lastPost => postsCount > 0 ? posts[postsCount - 1] : null;
  Exception get error => _state.exception;
  String get errorMessage => _state.exception.toString();
  bool get showFooter => _state.exception != null || _state.loading;
  bool get blurNsfw => _state.blurNsfw;

  bool shouldLoadMore(int position) {
    return position > postsCount - _loadThreshold && !loading && error == null;
  }

  void loadMore() {
    if (!loading) _dispatch(loadPosts(_state.feed, after: lastPost?.id));
  }

  void toggleBlurNsfw() {
    _dispatch(ChangeBlurNsfwAction(!blurNsfw));
  }
}
