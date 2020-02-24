import 'package:flutter/material.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/extensions/date.dart';

enum HeaderType { simple, extended }

class PostHeader extends StatelessWidget {
  final Post post;
  final HeaderType type;

  PostHeader(this.post, this.type);

  @override
  Widget build(BuildContext context) {
    switch (type) {
      case HeaderType.simple:
        return _simple(context);
      case HeaderType.extended:
        return _extended(context);
      default:
        throw 'Unrecognized type of header [$type]';
    }
  }

  Widget _simple(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(left: 16, right: 16, top: 16, bottom: 8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Text(
            post.title,
            style: Theme.of(context).textTheme.title,
          ),
          SizedBox(height: 8),
          _metadata(context),
        ],
      ),
    );
  }

  Widget _extended(BuildContext context) {
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
                      style: theme.textTheme.subtitle,
                    ),
                    SizedBox(height: 8),
                    _metadata(context),
                  ],
                ),
              ),
            ],
          ),
          Container(
            padding: EdgeInsets.only(top: 8),
            child: Text(
              post.title,
              style: theme.textTheme.title,
            ),
          )
        ],
      ),
    );
  }

  Widget _metadata(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return Row(
      crossAxisAlignment: CrossAxisAlignment.end,
      children: <Widget>[
        Flexible(
          child: Text(
            '/u/${post.author.name}',
            overflow: TextOverflow.fade,
            softWrap: false,
            style: theme.textTheme.body2,
          ),
        ),
        Container(
          padding: EdgeInsets.only(left: 8),
          child: Text(
            post.created.asTimeAgo(),
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
