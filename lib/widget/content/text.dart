import 'package:flutter/material.dart';
import 'package:starrit/model/post.dart';

class TextContent extends StatelessWidget {
  final Post post;

  TextContent(this.post);

  @override
  Widget build(BuildContext context) {
    if (post.text == null) {
      return Container();
    }
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 16),
      child: Text(post.text),
    );
  }
}
