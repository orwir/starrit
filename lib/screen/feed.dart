import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/action/feed.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/model/state.dart';

class FeedScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      onInit: (store) => {},
      converter: (store) => _ViewModel.fromStore(store),
      builder: (context, viewModel) => Scaffold(
        appBar: AppBar(title: Text(viewModel.title)),
        body: ListView.builder(
          itemBuilder: (context, index) {
            if (index > viewModel.postsCount - 10 && !viewModel.loading) {
              viewModel.loadMore();
            }
            if (index < viewModel.postsCount) {
              return _buildPost(context, viewModel.posts[index]);
            }
            if (index == viewModel.postsCount && viewModel.loading) {
              return LinearProgressIndicator();
            }
            if (index == viewModel.postsCount && viewModel.error != null) {
              // TODO: show error
            }
            return null;
          },
        ),
      ),
    );
  }

  Widget _buildPost(BuildContext context, Post post) {
    ThemeData theme = Theme.of(context);
    return Card(
      child: Container(
          padding: EdgeInsets.all(16),
          child: Text(
            post.title,
            style: theme.textTheme.title,
          )),
    );
  }
}

@immutable
class _ViewModel {
  final Feed feed;
  final bool loading;
  final List<Post> posts;
  final Exception error;
  final Function dispatch;

  _ViewModel({
    this.feed,
    this.loading,
    this.posts,
    this.error,
    this.dispatch,
  });

  _ViewModel.fromStore(Store<AppState> store)
      : this(
          feed: store.state.feedState.feed,
          loading: store.state.feedState.loading,
          posts: store.state.feedState.posts,
          error: store.state.feedState.error,
          dispatch: (dynamic action) => store.dispatch(action),
        );

  String get title => feed.asTitle;

  int get postsCount => posts.length;

  void loadMore() {
    String after = posts.length > 0 ? posts[posts.length - 1].id : null;
    dispatch(loadPosts(feed, after));
  }
}
