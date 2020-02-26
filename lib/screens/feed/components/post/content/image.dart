import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:starrit/models/image.dart';
import 'package:starrit/models/post.dart';
import 'package:starrit/models/state.dart';

class ImageContent extends StatelessWidget {
  final Post post;

  ImageContent(this.post);

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