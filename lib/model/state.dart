import 'package:flutter/foundation.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';

@immutable
class AppState {
  final Feed feed;
  final List<Post> posts;

  AppState({this.feed, this.posts});

  @override
  String toString() => '{feed: "$feed", posts: ${posts?.length ?? 0}}';
}
