import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:url_launcher/url_launcher.dart';

/// Returns image url based on app state.
///
/// May return blurred version of image if:
/// * post marked as "spoiler".
/// * post marked as "nsfw" and app configured to blur nsfw content.
///
/// With [force] flag always return non-blurred version.
String _resolveImage(
  BuildContext context,
  Post post, {
  bool force = false,
}) {
  final state = StoreProvider.of<AppState>(context).state;
  if (!force && post.image.blurred != null) {
    if (post.spoiler || (post.nsfw && state.blurNsfw)) {
      return post.image.blurred;
    }
  }
  return post.image.source;
}

/// Resolves content type and returns corresponding view.
class PostContent extends StatelessWidget {
  final Post post;

  PostContent(this.post);

  @override
  Widget build(BuildContext context) {
    switch (post.type) {
      case PostType.image:
        return PostImageContent(post);
      default:
        return PostLinkContent(post);
    }
  }
}

/// Renders content as image.
class PostImageContent extends StatelessWidget {
  final Post post;

  PostImageContent(this.post);

  @override
  Widget build(BuildContext context) => AspectRatio(
        aspectRatio: post.image.ratio,
        child: Image.network(
          _resolveImage(context, post),
          fit: BoxFit.fill,
          width: post.image.width?.toDouble(),
          height: post.image.height?.toDouble(),
        ),
      );
}

/// Renders content as link.
class PostLinkContent extends StatelessWidget {
  final Post post;

  PostLinkContent(this.post);

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final colors = theme.colorScheme;
    return Container(
      height: 140,
      child: Stack(
        alignment: Alignment.bottomCenter,
        children: [
          if (post.image != null)
            Positioned.fill(
              child: Image.network(
                _resolveImage(context, post),
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
              color: colors.surface.withOpacity(.8),
              child: Row(
                children: [
                  Text(post.domain, style: theme.textTheme.headline6),
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
