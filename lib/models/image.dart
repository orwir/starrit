import 'package:flutter/foundation.dart';
import 'package:starrit/extensions/json.dart';
import 'package:starrit/extensions/object.dart';

@immutable
class ImageData {
  final String url;
  final int width;
  final int height;

  ImageData({@required this.url, @required this.width, @required this.height});

  ImageData.fromJson(Map<String, dynamic> json)
      : this(
          url: json['url'],
          width: json['width'],
          height: json['height'],
        );

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

@immutable
class ImagePack {
  final ImageData preview;
  final ImageData source;
  final ImageData blurred;

  ImagePack({
    @required this.preview,
    @required this.source,
    @required this.blurred,
  });

  ImagePack.fromJson(Map<String, dynamic> json)
      : this(
          preview: json.image('preview.images[0].resolutions[0]'),
          source: json.image('preview.images[0].source'),
          blurred: json.image('preview.images[0].variants.nsfw.resolutions[0]'),
        );

  @override
  int get hashCode =>
      (preview?.hashCode ?? 0) ^
      (source?.hashCode ?? 0) ^
      (blurred?.hashCode ?? 0);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is ImagePack &&
          runtimeType == other.runtimeType &&
          preview == other.preview &&
          source == other.source &&
          blurred == other.blurred;
}

extension _ on Map<String, dynamic> {
  ImageData image(String key) {
    return string(key)?.into((json) => ImageData.fromJson(json));
  }
}
