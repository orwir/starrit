import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:share/share.dart';
import 'package:starrit/feed/model/post.dart';

class PostToolbar extends StatelessWidget {
  final Post post;
  final numFormat = NumberFormat.compact();

  PostToolbar(this.post);

  @override
  Widget build(BuildContext context) => Container(
        padding: EdgeInsets.symmetric(horizontal: 8),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.max,
          children: [
            IconButton(
              icon: Icon(Icons.arrow_drop_up),
              onPressed: null,
            ),
            post.hideScore
                ? Icon(Icons.access_time, size: 16)
                : Text(numFormat.format(post.score)),
            IconButton(
              icon: Icon(Icons.arrow_drop_down),
              onPressed: null,
            ),
            IconButton(
              icon: Icon(Icons.comment),
              onPressed: null,
            ),
            Text(numFormat.format(post.comments)),
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
