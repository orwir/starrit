import 'package:flutter/material.dart';
import 'package:starrit/model/image.dart';
import 'package:url_launcher/url_launcher.dart';

class LinkContent extends StatelessWidget {
  final String link;
  final String displayText;
  final ImageData cover;

  LinkContent(this.link, {this.displayText, this.cover});

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    ColorScheme colors = theme.colorScheme;
    return Container(
      height: 120,
      child: Stack(
        alignment: Alignment.bottomCenter,
        children: <Widget>[
          if (cover != null)
            Positioned.fill(
              child: Image.network(
                cover.url,
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
                    displayText,
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
                onTap: () => launch(link),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
