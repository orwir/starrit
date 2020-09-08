import 'package:flutter/material.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:starrit/feed/widget/post_header.dart';

enum Header { simple, extended }

class PostView extends StatelessWidget {
  final Post post;
  final Header header;

  PostView(this.post, {this.header = Header.extended})
      : assert(post != null),
        assert(header != null),
        super(key: ValueKey(post.id));

  @override
  Widget build(BuildContext context) => Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          header == Header.simple
              ? SimplePostHeader(post)
              : ExtendedPostHeader(post),
        ],
      );
}
