import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/feed/widgets/post.dart';
import 'package:starrit/preferences/actions.dart';
import 'package:starrit/search/screen.dart';

import 'models/feed.dart';
import 'models/post.dart';

class FeedScreen extends StatelessWidget {
  static const routeName = '/feed';

  FeedScreen(this.feed);

  final Feed feed;
  final ScrollController _scrollController = ScrollController();

  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      distinct: true,
      onInit: (store) {
        final state = store.state[feed];
        if (state == null || (!state.loading && state.posts.isEmpty)) {
          store.dispatch(FeedRequestAction(feed));
        }
      },
      converter: (store) => _ViewModel.fromStore(feed, store),
      builder: (context, viewModel) => WillPopScope(
        onWillPop: () async {
          viewModel.dispose();
          return true;
        },
        child: Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
            title: Text(viewModel.title),
            actions: <Widget>[
              IconButton(
                icon: Icon(Icons.arrow_upward),
                onPressed: () {
                  _scrollController.jumpTo(0);
                  viewModel.requestData(reset: true);
                },
              ),
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
            controller: _scrollController,
            itemCount: viewModel.itemCount,
            separatorBuilder: (context, index) => Divider(height: 1),
            itemBuilder: (context, index) {
              if (viewModel.shouldLoadMore(index)) {
                viewModel.requestData(after: viewModel.next);
              }

              if (index < viewModel.postCount) {
                return PostView(viewModel.posts[index]);
              }
              if (viewModel.hasError) {
                return _buildErrorItem(viewModel);
              }
              return LinearProgressIndicator();
            },
          ),
        ),
      ),
    );
  }

  Widget _buildErrorItem(_ViewModel viewModel) {
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
            onPressed: () => viewModel.requestData(after: viewModel.next),
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
  int get postCount => _state.posts.length;
  int get itemCount => postCount + ((hasError || loading) ? 1 : 0);
  String get next => _state.next;
  bool get hasError => _state.exception != null;
  String get errorMessage => _state.exception?.toString() ?? '';

  bool shouldLoadMore(int position) {
    return (position > postCount - _fetchThreshold) && !loading && !hasError;
  }

  void requestData({bool reset = false, String after}) {
    if (!loading)
      _dispatch(FeedRequestAction(
        _state.feed,
        reset: reset,
        after: after,
      ));
  }

  void toggleBlurNsfw() {
    _dispatch(BlurNsfwChangeAction(!blurNsfw));
  }

  void openSearch(BuildContext context) {
    Navigator.pushNamed(context, SearchScreen.routeName);
  }

  void dispose() async {
    await Future.delayed(Duration(milliseconds: 500));
    _dispatch(FeedDisposeAction(_state.feed));
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
