import 'package:flutter/material.dart';
import 'package:starrit/common/util/date.dart';
import 'package:starrit/feed/model/post.dart';

class SimplePostHeader extends StatelessWidget {
  final Post post;

  SimplePostHeader(this.post);

  @override
  Widget build(BuildContext context) => Container(
        padding: EdgeInsets.only(left: 16, right: 16, top: 16, bottom: 8),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(post.title, style: Theme.of(context).textTheme.headline6),
            SizedBox(height: 8),
            PostHeaderMetadata(post),
          ],
        ),
      );
}

class ExtendedPostHeader extends StatelessWidget {
  final Post post;

  ExtendedPostHeader(this.post);

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return Container(
      padding: EdgeInsets.only(left: 16, right: 16, top: 16, bottom: 8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Row(
            children: <Widget>[
              if (post.subreddit.icon != null)
                Padding(
                  padding: EdgeInsets.only(right: 8),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(20),
                    child: Image.network(
                      post.subreddit.icon,
                      width: 40,
                      height: 40,
                    ),
                  ),
                ),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text(
                      '/r/${post.subreddit.name}',
                      style: theme.textTheme.subtitle2,
                    ),
                    SizedBox(height: 8),
                    PostHeaderMetadata(post),
                  ],
                ),
              ),
            ],
          ),
          Container(
            padding: EdgeInsets.only(top: 8),
            child: Text(
              post.title,
              style: theme.textTheme.headline6,
            ),
          )
        ],
      ),
    );
  }
}

class PostHeaderMetadata extends StatelessWidget {
  final Post post;

  PostHeaderMetadata(this.post);

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return Row(
      crossAxisAlignment: CrossAxisAlignment.end,
      children: <Widget>[
        Flexible(
          child: Text(
            '${post.author.deleted ? "" : "/u/"}${post.author.name}',
            overflow: TextOverflow.fade,
            softWrap: false,
            style: theme.textTheme.bodyText1,
          ),
        ),
        Container(
          padding: EdgeInsets.only(left: 8),
          child: Text(
            post.created.timeAgo,
            style: theme.textTheme.caption,
          ),
        ),
        if (!post.selfDomain)
          Container(
            padding: EdgeInsets.only(left: 8),
            child: Text(
              post.domain,
              style: theme.textTheme.caption,
            ),
          ),
        if (post.nsfw)
          Container(
            padding: EdgeInsets.only(left: 8),
            child: Icon(
              Icons.visibility_off,
              size: 16,
              color: theme.colorScheme.primary,
            ),
          ),
        if (post.spoiler)
          Container(
            padding: EdgeInsets.only(left: 8),
            child: Icon(
              Icons.warning,
              size: 16,
              color: theme.colorScheme.primary,
            ),
          ),
      ],
    );
  }
}
