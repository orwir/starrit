import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:share/share.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/util/content.dart';
import 'package:starrit/util/date.dart';
import 'package:starrit/widget/content/gif.dart';
import 'package:starrit/widget/content/image.dart';
import 'package:starrit/widget/content/link.dart';
import 'package:starrit/widget/content/text.dart';
import 'package:starrit/widget/content/video.dart';

class PostView extends StatelessWidget {
  final Post post;
  final HeaderType header;

  PostView(this.post, {this.header = HeaderType.extended});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        _PostHeader(post, header),
        _PostContent(post),
        _PostToolbar(post),
      ],
    );
  }
}

enum HeaderType { simple, extended }

class _PostHeader extends StatelessWidget {
  final Post post;
  final HeaderType type;

  _PostHeader(this.post, this.type);

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

class _PostContent extends StatelessWidget {
  final Post post;

  _PostContent(this.post);

  @override
  Widget build(BuildContext context) {
    switch (post.type) {
      case ContentType.video:
        return VideoContent(post);
      case ContentType.gif:
        return GifContent(post);
      case ContentType.image:
        return ImageContent(post);
      case ContentType.text:
        return TextContent(post);
      default:
        return LinkContent(post);
    }
  }
}

class _PostToolbar extends StatelessWidget {
  final Post post;
  final NumberFormat numberFormat = NumberFormat.compact();

  _PostToolbar(this.post);

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
