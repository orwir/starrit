import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/model/state.dart';

class FeedScreen extends StatelessWidget {
  final Feed feed;

  FeedScreen(this.feed);

  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, _ViewModel>(
      converter: (store) => _ViewModel.fromState(feed, store.state),
      builder: (context, viewModel) => Scaffold(
        appBar: AppBar(
          title: Text('${viewModel.feed}'),
        ),
      ),
    );
  }
}

@immutable
class _ViewModel {
  final Feed feed;
  final List<Post> posts;

  _ViewModel(this.feed, this.posts);

  _ViewModel.fromState(Feed feed, AppState state)
      : this(feed, state.feeds[feed].posts);
}
