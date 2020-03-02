import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:intl/intl.dart';
import 'package:share/share.dart';
import 'package:starrit/models/image.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/extensions/date.dart';
import 'package:starrit/models/state.dart';
import 'package:url_launcher/url_launcher.dart';

enum HeaderType { simple, extended }

class PostView extends StatelessWidget {
  final Post post;
  final HeaderType header;

  PostView(this.post, {this.header = HeaderType.extended});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: <Widget>[
        _Header(post, header),
        _Content(post),
        _Toolbar(post),
      ],
    );
  }
}

class _Header extends StatelessWidget {
  final Post post;
  final HeaderType type;

  _Header(this.post, this.type);

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

class _Content extends StatelessWidget {
  final Post post;

  _Content(this.post);

  @override
  Widget build(BuildContext context) {
    switch (post.type) {
      case PostType.video:
        return _VideoContent(post);
      case PostType.gif:
        return _GifContent(post);
      case PostType.image:
        return _ImageContent(post);
      case PostType.text:
        return _TextContent(post);
      default:
        return _LinkContent(post);
    }
  }
}

class _Toolbar extends StatelessWidget {
  final Post post;
  final NumberFormat numberFormat = NumberFormat.compact();

  _Toolbar(this.post);

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

class _GifContent extends StatelessWidget {
  final Post post;

  _GifContent(this.post);

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      child: Text(
        'Gif Content',
        style: Theme.of(context).textTheme.body2.copyWith(
              color: Colors.red[700],
              fontWeight: FontWeight.bold,
            ),
      ),
    );
  }
}

class _ImageContent extends StatelessWidget {
  final Post post;

  _ImageContent(this.post);

  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, bool>(
      distinct: true,
      converter: (store) => store.state.blurNsfw,
      builder: (context, blurNsfw) {
        final image = _resolveImage(blurNsfw);
        return AspectRatio(
          aspectRatio: image.width / image.height,
          child: Image.network(
            image.url,
            fit: BoxFit.fill,
            width: image.width,
            height: image.height,
          ),
        );
      },
    );
  }

  ImageData _resolveImage(bool blurNsfw) {
    return (post.spoiler || (post.nsfw && blurNsfw))
        ? post.images.blurred
        : post.images.source;
  }
}

class _LinkContent extends StatelessWidget {
  final Post post;

  _LinkContent(this.post);

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    ColorScheme colors = theme.colorScheme;
    return Container(
      height: 120,
      child: Stack(
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          if (post.images.source != null)
            Positioned.fill(
              child: Image.network(
                post.images.source.url,
                fit: BoxFit.cover,
              ),
            ),
          Positioned(
            bottom: 0,
            left: 0,
            right: 0,
            child: Container(
              height: 40,
              alignment: Alignment.centerLeft,
              padding: EdgeInsets.symmetric(horizontal: 16),
              color: colors.surface.withOpacity(.6),
              child: Row(
                children: <Widget>[
                  Text(
                    post.domain,
                    style: theme.textTheme.title,
                  ),
                  Spacer(),
                  Icon(Icons.open_in_new),
                ],
              ),
            ),
          ),
          Positioned.fill(
            child: Material(
              color: Colors.transparent,
              child: InkWell(onTap: () => launch(post.contentUrl)),
            ),
          ),
        ],
      ),
    );
  }
}

class _TextContent extends StatelessWidget {
  final Post post;

  _TextContent(this.post);

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

class _VideoContent extends StatelessWidget {
  final Post post;

  _VideoContent(this.post);

  @override
  Widget build(BuildContext context) {
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
