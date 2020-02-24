import 'package:flutter/material.dart';
import 'package:starrit/model/post.dart';

class VideoContent extends StatelessWidget {
  final Post post;

  VideoContent(this.post);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
      alignment: Alignment.center,
      child: Text(
        'Video Content',
        style: Theme.of(context).textTheme.body2.copyWith(
              color: Colors.red[700],
              fontWeight: FontWeight.bold,
            ),
      ),
    );
  }
}
