import 'package:flutter/material.dart';
import 'package:starrit/models/post.dart';

import 'content/main.dart';
import 'header.dart';
import 'toolbar.dart';

class PostView extends StatelessWidget {
  final Post post;
  final HeaderType header;

  PostView(this.post, {this.header = HeaderType.extended});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        PostHeader(post, header),
        PostContent(post),
        PostToolbar(post),
      ],
    );
  }
}
