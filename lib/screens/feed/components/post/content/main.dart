import 'package:flutter/material.dart';
import 'package:starrit/models/post.dart';

import 'gif.dart';
import 'image.dart';
import 'link.dart';
import 'text.dart';
import 'video.dart';

class PostContent extends StatelessWidget {
  final Post post;

  PostContent(this.post);

  @override
  Widget build(BuildContext context) {
    switch (post.type) {
      case PostType.video:
        return VideoContent(post);
      case PostType.gif:
        return GifContent(post);
      case PostType.image:
        return ImageContent(post);
      case PostType.text:
        return TextContent(post);
      default:
        return LinkContent(post);
    }
  }
}
