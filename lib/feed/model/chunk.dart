import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/post.dart';

@immutable
class Chunk {
  final List<Post> posts;
  final String next;

  Chunk(this.posts, this.next);

  @override
  String toString() => 'posts:${posts.length}, next:$next';
}
