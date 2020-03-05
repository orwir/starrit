import 'dart:convert';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:redux/redux.dart';
import 'package:redux_thunk/redux_thunk.dart';
import 'package:starrit/models/feed.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/services/feed.dart';
import 'package:starrit/utils/json.dart';

@immutable
class FeedRequestAction {
  FeedRequestAction(this.feed);

  final Feed feed;

  @override
  String toString() => '{type:$runtimeType, feed:$feed}';
}

@immutable
class FeedDisposeAction {
  FeedDisposeAction(this.feed);

  final Feed feed;

  @override
  String toString() => '{type:$runtimeType, feed:$feed}';
}

@immutable
class FeedResponseSuccessAction {
  FeedResponseSuccessAction(this.feed, this.posts);

  final Feed feed;
  final Iterable<Post> posts;

  @override
  String toString() => '{type:$runtimeType, feed:$feed, posts:${posts.length}}';
}

@immutable
class FeedResponseFailureAction {
  FeedResponseFailureAction(this.feed, this.exception);

  final Feed feed;
  final Exception exception;

  @override
  String toString() => '{type:$runtimeType, feed:$feed, exception:$exception}';
}

ThunkAction<AppState> fetchPosts(Feed feed, {String after}) {
  return (Store<AppState> store) async {
    store.dispatch(FeedRequestAction(feed));
    try {
      final response = await listing(
        domain: 'reddit.com',
        feed: feed,
        after: after,
      );
      if (response.statusCode == 200) {
        final Map<String, dynamic> result = jsonDecode(response.body);
        final posts = result
            .get<List>('data.children')
            .map((json) => Post.fromJson(json['data']))
            .toList();
        store.dispatch(FeedResponseSuccessAction(feed, posts));
      } else {
        store.dispatch(FeedResponseFailureAction(
          feed,
          HttpException('request failed with code: ${response.statusCode}'),
        ));
      }
    } on Exception catch (e) {
      store.dispatch(FeedResponseFailureAction(feed, e));
    }
  };
}
