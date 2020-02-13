import 'package:flutter/foundation.dart';

@immutable
class ImageData {
  final String url;
  final int width;
  final int height;

  ImageData({@required this.url, @required this.width, @required this.height});
}
