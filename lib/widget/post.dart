import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/util/date.dart';

enum Header { simple, extended }

class PostView extends StatelessWidget {
  final Post post;
  final Header header;
  final NumberFormat numberFormat = NumberFormat.compact();

  PostView(this.post, {this.header = Header.extended});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        _header(context),
        _content(context),
        _toolbar(context),
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
    ThemeData theme = Theme.of(context);
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

  Widget _extendedHeader(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return Container(
      padding: EdgeInsets.only(left: 16, right: 16, top: 16, bottom: 8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Row(
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
              style: Theme.of(context).textTheme.title,
            ),
          )
        ],
      ),
    );
  }

  Widget _content(BuildContext context) {
    return Container(
      color: Colors.grey[200],
      child: Stack(
        children: <Widget>[
          if (post.imageSource != null) Image.network(post.imageSource.url),
        ],
      ),
    );
  }

  Widget _toolbar(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 16),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          IconButton(
            icon: Icon(Icons.arrow_drop_up),
            iconSize: 32,
            onPressed: null,
          ),
          Text(numberFormat.format(post.score)),
          IconButton(
            icon: Icon(Icons.arrow_drop_down),
            iconSize: 32,
            onPressed: null,
          ),
          IconButton(
            icon: Icon(Icons.comment),
            iconSize: 20,
            onPressed: null,
          ),
          Text(numberFormat.format(post.comments)),
          Spacer(),
          IconButton(
            icon: Icon(Icons.share),
            iconSize: 20,
            onPressed: null,
          ),
          IconButton(
            icon: Icon(Icons.more_vert),
            onPressed: null,
          ),
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
              color: theme.primaryColor,
            ),
          ),
        if (post.spoiler)
          Container(
            padding: EdgeInsets.only(left: 8),
            child: Icon(
              Icons.warning,
              size: 16,
              color: theme.primaryColor,
            ),
          ),
      ],
    );
  }
}
