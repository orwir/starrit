import 'package:flutter/material.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:starrit/common/util/date.dart';

enum Header { simple, extended }

class PostView extends StatelessWidget {
  final Post post;
  final Header header;

  PostView(this.post, {this.header = Header.extended})
      : assert(post != null),
        assert(header != null);

  @override
  Widget build(BuildContext context) => Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          _Header(post, header),
        ],
      );
}

class _Header extends StatelessWidget {
  final Post post;
  final Header type;

  _Header(this.post, this.type);

  @override
  Widget build(BuildContext context) {
    switch (type) {
      case Header.simple:
        return simple(context);
      case Header.extended:
        return extended(context);
      default:
        throw 'Unrecognized type of header { $type }';
    }
  }

  Widget simple(BuildContext context) => Container(
        padding: EdgeInsets.only(left: 16, right: 16, top: 16, bottom: 8),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(post.title, style: Theme.of(context).textTheme.headline6),
            SizedBox(height: 8),
            metadata(context),
          ],
        ),
      );

  Widget extended(BuildContext context) {
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
                    metadata(context),
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

  Widget metadata(BuildContext context) {
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
            post.created.asTimeAgo,
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

/*

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
          _buildScore(),
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

  Widget _buildScore() => post.hideScore
      ? Icon(Icons.access_time, size: 16)
      : Text(numberFormat.format(post.score));
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
        style: Theme.of(context).textTheme.bodyText1.copyWith(
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
        if (post.image.hasSize) {
          return AspectRatio(
              aspectRatio: post.image.width / post.image.height,
              child: _buildImage(context, blurNsfw));
        }
        return _buildImage(context, blurNsfw);
      },
    );
  }

  Widget _buildImage(BuildContext context, bool blurNsfw) {
    return Image.network(
      // TODO: simplify condition
      ((post.spoiler || (post.nsfw && blurNsfw)) && post.image.blurred != null)
          ? post.image.blurred
          : post.image.source,
      fit: BoxFit.fill,
      width: post.image.width,
      height: post.image.height,
    );
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
          if (post.image != null)
            Positioned.fill(
              child: Image.network(
                post.image.source,
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
                    style: theme.textTheme.headline6,
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
        style: Theme.of(context).textTheme.bodyText1.copyWith(
              color: Colors.red[700],
              fontWeight: FontWeight.bold,
            ),
      ),
    );
  }
}

*/
