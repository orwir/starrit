import 'dart:convert';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:redux/redux.dart';
import 'package:redux_thunk/redux_thunk.dart';
import 'package:starrit/models/feed.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/services/feed.dart';

@immutable
class StartLoadingPostsAction {
  final Feed feed;

  StartLoadingPostsAction(this.feed);

  @override
  String toString() => '$runtimeType[$feed]';
}

@immutable
class LoadingPostsSuccessAction {
  final Feed feed;
  final List<Post> posts;

  LoadingPostsSuccessAction(this.feed, this.posts);

  @override
  String toString() => '$runtimeType[$feed, posts=${posts.length}]';
}

@immutable
class LoadingPostsFailureAction {
  final Feed feed;
  final Exception exception;

  LoadingPostsFailureAction(this.feed, this.exception);

  @override
  String toString() => '$runtimeType[$feed, error=$exception]';
}

@immutable
class ChangeBlurNsfwAction {
  final bool blurNsfw;

  ChangeBlurNsfwAction(this.blurNsfw);
}

ThunkAction<AppState> loadPosts(Feed feed, {String after}) {
  return (Store<AppState> store) async {
    store.dispatch(StartLoadingPostsAction(feed));
    try {
      final response = await listing(
        domain: 'reddit.com',
        feed: feed,
        after: after,
      );
      if (response.statusCode == 200) {
        final result = jsonDecode(response.body);
        final posts = (result['data']['children'] as List<dynamic>)
            .map((json) => Post.fromJson(json['data']))
            .toList();
        store.dispatch(LoadingPostsSuccessAction(feed, posts));
      } else {
        store.dispatch(LoadingPostsFailureAction(
          feed,
          HttpException('request failed with code: ${response.statusCode}'),
        ));
      }
    } on Exception catch (e) {
      store.dispatch(LoadingPostsFailureAction(feed, e));
    }
  };
}
