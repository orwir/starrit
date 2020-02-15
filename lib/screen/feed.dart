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
    return StoreConnector<AppState, List<Post>>(
      converter: (store) => store.state.posts,
      builder: (context, viewModel) => Scaffold(
        appBar: AppBar(
          title: Text('$feed'),
        ),
      ),
    );
  }
}
