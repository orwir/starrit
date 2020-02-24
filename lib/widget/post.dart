import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/util/content.dart';
import 'package:starrit/util/date.dart';
import 'package:starrit/widget/content/gif.dart';
import 'package:starrit/widget/content/image.dart';
import 'package:starrit/widget/content/link.dart';
import 'package:starrit/widget/content/text.dart';
import 'package:starrit/widget/content/video.dart';

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
              if (post.subreddit.icon != null)
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
    switch (post.type) {
      case ContentType.video:
        return VideoContent(post.video);
      case ContentType.gif:
        return GifContent(post.gif);
      case ContentType.image:
        return ImageContent(
          preview: post.imagePreview,
          source: post.imageSource,
        );
      case ContentType.text:
        return TextContent(post.text);
      default:
        return LinkContent(
          post.contentUrl,
          displayText: post.domain,
          cover: post.imageSource,
        );
    }
  }

  Widget _toolbar(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 8),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          IconButton(
            icon: Icon(Icons.arrow_drop_up, size: 32),
            onPressed: null,
          ),
          Text(numberFormat.format(post.score)),
          IconButton(
            icon: Icon(Icons.arrow_drop_down, size: 32),
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
