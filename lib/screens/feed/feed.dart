import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/actions/feed.dart';
import 'package:starrit/actions/preference.dart';
import 'package:starrit/models/feed.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/screens/feed/components/post.dart';
import 'package:starrit/screens/search/search.dart';

class FeedScreen extends StatelessWidget {
  static const routeName = '/feed';

  FeedScreen(this.feed);

  final Feed feed;

  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      distinct: true,
      onInit: (store) {
        final state = store.state[feed];
        if (state == null || (!state.loading && state.posts.isEmpty)) {
          store.dispatch(fetchPosts(feed));
        }
      },
      converter: (store) => _ViewModel.fromStore(feed, store),
      builder: (context, viewModel) => Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false,
          title: Text(viewModel.title),
          actions: <Widget>[
            IconButton(
              icon: Icon(
                viewModel.blurNsfw ? Icons.visibility_off : Icons.visibility,
              ),
              onPressed: viewModel.toggleBlurNsfw,
            ),
            IconButton(
              icon: Icon(Icons.search),
              onPressed: () => viewModel.openSearch(context),
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
              return _buildListFooter(viewModel);
            }
            return LinearProgressIndicator();
          },
        ),
      ),
    );
  }

  Widget _buildListFooter(_ViewModel viewModel) {
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
  static const _fetchThreshold = 10;

  _ViewModel.fromStore(Feed feed, Store<AppState> store)
      : blurNsfw = store.state.blurNsfw,
        _state = store.state[feed],
        _dispatch = store.dispatch;

  final blurNsfw;
  final FeedState _state;
  final Function _dispatch;

  String get title => _state.feed.label;
  bool get loading => _state.loading;
  List<Post> get posts => _state.posts;
  int get postsCount => _state.posts.length;
  Post get lastPost => posts.isNotEmpty ? posts.last : null;
  Exception get error => _state.exception;
  String get errorMessage => _state.exception?.toString() ?? '';
  bool get showFooter => _state.exception != null || _state.loading;

  bool shouldLoadMore(int position) {
    return position > postsCount - _fetchThreshold && !loading && error == null;
  }

  void loadMore() {
    if (!loading) _dispatch(fetchPosts(_state.feed, after: lastPost?.id));
  }

  void toggleBlurNsfw() {
    _dispatch(BlurNsfwChangeAction(!blurNsfw));
  }

  void openSearch(BuildContext context) {
    Navigator.pushNamed(context, SearchScreen.routeName);
  }

  @override
  int get hashCode => _state.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is _ViewModel &&
          runtimeType == other.runtimeType &&
          blurNsfw == other.blurNsfw &&
          _state == other._state;
}
