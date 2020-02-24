import 'package:flutter/material.dart';
import 'package:starrit/model/post.dart';
import 'package:url_launcher/url_launcher.dart';

class LinkContent extends StatelessWidget {
  final Post post;

  LinkContent(this.post);

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
              child: InkWell(
                onTap: () => launch(post.contentUrl),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
