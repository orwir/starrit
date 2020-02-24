import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:share/share.dart';
import 'package:starrit/models/post.dart';

class PostToolbar extends StatelessWidget {
  final Post post;
  final NumberFormat numberFormat = NumberFormat.compact();

  PostToolbar(this.post);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 8),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          IconButton(
            icon: Icon(Icons.arrow_drop_up),
            onPressed: null,
          ),
          Text(numberFormat.format(post.score)),
          IconButton(
            icon: Icon(Icons.arrow_drop_down),
            onPressed: null,
          ),
          IconButton(
            icon: Icon(Icons.comment),
            onPressed: null,
          ),
          Text(numberFormat.format(post.comments)),
          Spacer(),
          IconButton(
            icon: Icon(Icons.share),
            onPressed: () => Share.share(post.contentUrl),
          ),
          IconButton(
            icon: Icon(Icons.more_vert),
            onPressed: null,
          ),
        ],
      ),
    );
  }
}
