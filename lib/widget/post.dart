import 'package:flutter/material.dart';
import 'package:starrit/model/post.dart';

enum Header { simple, extended }

class PostView extends StatelessWidget {
  final Post post;
  final Header header;

  PostView(this.post, {this.header = Header.extended});

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        _header(context),
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          child: Text(
            post.title,
            style: theme.textTheme.title,
          ),
        )
      ],
    );
  }

  Widget _header(BuildContext context) {
    switch (header) {
      case Header.simple:
        return _simpleHeader(context);
      case Header.extended:
        return _extendedHeader(context);
      default:
        throw 'Header type [$header] not implemented';
    }
  }

  Widget _simpleHeader(BuildContext context) {
    // TODO: implement simple header
    return _extendedHeader(context);
  }

  Widget _extendedHeader(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return Padding(
      padding: EdgeInsets.only(left: 16, right: 16, top: 16),
      child: Row(
        children: <Widget>[
          if (post.subreddit.icon?.isNotEmpty)
            Padding(
              padding: EdgeInsets.only(right: 16),
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
                Row(
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
                    Padding(
                      child: Text(
                        post.created,
                        style: theme.textTheme.caption,
                      ),
                      padding: EdgeInsets.only(left: 8),
                    ),
                    Padding(
                      child: Text(
                        post.domain,
                        style: theme.textTheme.caption,
                      ),
                      padding: EdgeInsets.only(left: 8),
                    ),
                    if (post.nsfw)
                      Padding(
                        padding: EdgeInsets.only(left: 8),
                        child: Icon(
                          Icons.visibility_off,
                          size: 16,
                          color: theme.primaryColor,
                        ),
                      ),
                    if (post.spoiler)
                      Padding(
                        padding: EdgeInsets.only(left: 8),
                        child: Icon(
                          Icons.warning,
                          size: 16,
                          color: theme.primaryColor,
                        ),
                      ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
