import 'package:flutter/material.dart';
import 'package:starrit/models/post.dart';

class ImageContent extends StatelessWidget {
  final Post post;

  ImageContent(this.post);

  @override
  Widget build(BuildContext context) {
    return Image.network(post.images.source.url);
  }
}
