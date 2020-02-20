import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/action/feed.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/model/state.dart';

class FeedScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      onInit: (store) => {},
      converter: _ViewModel.fromStore,
      builder: (context, viewModel) => Scaffold(
        appBar: AppBar(title: Text(viewModel.title)),
        body: ListView.separated(
          itemCount: viewModel.postsCount + (viewModel.showFooter ? 1 : 0),
          separatorBuilder: (context, index) => Divider(),
          itemBuilder: (context, index) {
            if (viewModel.shouldLoadMore(index)) viewModel.loadMore();

            if (index < viewModel.postsCount) {
              return _buildPost(context, viewModel.posts[index]);
            }
            if (index == viewModel.postsCount && viewModel.loading) {
              return LinearProgressIndicator();
            }
            if (index == viewModel.postsCount && viewModel.error != null) {
              return _buildFooter(viewModel);
            }
            return null;
          },
        ),
      ),
    );
  }

  Widget _buildPost(BuildContext context, Post post) {
    ThemeData theme = Theme.of(context);
    return Container(
      padding: EdgeInsets.all(16),
      child: Text(
        post.title,
        style: theme.textTheme.title,
      ),
    );
  }

  Widget _buildFooter(_ViewModel viewModel) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 16),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
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
  final FeedState _state;
  final Function _dispatch;
  final int _loadThreshold = 10;

  _ViewModel._(this._state, this._dispatch);

  static _ViewModel fromStore(Store<AppState> store) {
    return _ViewModel._(
      store.state.feedState,
      (dynamic action) => store.dispatch(action),
    );
  }

  String get title => _state.feed.asTitle;
  bool get loading => _state.loading;
  List<Post> get posts => _state.posts;
  int get postsCount => _state.posts.length;
  Post get lastPost => postsCount > 0 ? posts[postsCount - 1] : null;
  Exception get error => _state.error;
  String get errorMessage => _state.error.toString();
  bool get showFooter => _state.error != null || _state.loading;

  bool shouldLoadMore(int position) {
    return position > postsCount - _loadThreshold && !loading && error == null;
  }

  void loadMore() {
    if (!loading) _dispatch(loadPosts(_state.feed, lastPost?.id));
  }
}
