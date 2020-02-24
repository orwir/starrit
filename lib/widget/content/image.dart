import 'package:flutter/material.dart';
import 'package:starrit/model/image.dart';

class ImageContent extends StatelessWidget {
  final ImageData preview;
  final ImageData source;

  ImageContent({
    @required this.preview,
    @required this.source,
  });

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
      alignment: Alignment.center,
      child: Text(
        'Image Content',
        style: Theme.of(context).textTheme.body2.copyWith(
              color: Colors.red[700],
              fontWeight: FontWeight.bold,
            ),
      ),
    );
  }
}
