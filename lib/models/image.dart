import 'package:flutter/foundation.dart';
import 'package:starrit/utils/json.dart';
import 'package:starrit/utils/object.dart';

@immutable
class PostImage {
  PostImage({@required this.url, @required this.width, @required this.height});

  PostImage.fromJson(Map<String, dynamic> json)
      : this(
          url: json['url'],
          width: json.get<int>('width').toDouble(),
          height: json.get<int>('height').toDouble(),
        );

  final String url;
  final double width;
  final double height;

  @override
  String toString() => '{url:$url, size:${width}x$height}';

  @override
  int get hashCode => hash([url, width, height]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is PostImage &&
          runtimeType == other.runtimeType &&
          url == other.url &&
          width == other.width &&
          height == other.height;
}
