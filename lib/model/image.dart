import 'package:flutter/foundation.dart';

@immutable
class ImageData {
  final String url;
  final int width;
  final int height;

  ImageData({@required this.url, @required this.width, @required this.height});

  @override
  String toString() => '$runtimeType[url=$url, size:${width}x$height]';

  @override
  int get hashCode => url.hashCode ^ width.hashCode ^ height.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is ImageData &&
          runtimeType == other.runtimeType &&
          url == other.url &&
          width == other.width &&
          height == other.height;
}
