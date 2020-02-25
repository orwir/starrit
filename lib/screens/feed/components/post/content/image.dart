import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
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
      builder: (context, blurNsfw) => Image.network(
        blurNsfw ? post.images.blurred.url : post.images.source.url,
        fit: BoxFit.fill,
      ),
    );
  }
}
