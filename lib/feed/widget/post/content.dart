import 'package:flutter/material.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:url_launcher/url_launcher.dart';

class PostContent extends StatelessWidget {
  final Post post;

  PostContent(this.post);

  @override
  Widget build(BuildContext context) {
    switch (post.type) {
      default:
        return PostLinkContent(post);
    }
  }
}

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
              child: Image.network(post.image.source, fit: BoxFit.cover),
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
