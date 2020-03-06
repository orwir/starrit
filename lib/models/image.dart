import 'package:flutter/foundation.dart';
import 'package:starrit/utils/object.dart';

@immutable
class PostImage {
  PostImage({
    @required this.source,
    this.width,
    this.height,
    this.preview,
    this.blurred,
  });

  final double width;
  final double height;
  final String source;
  final String preview;
  final String blurred;

  bool get hasSize => width != null && height != null;

  @override
  String toString() =>
      '{source=$source, size:${width}x$height, preview:$preview, blurred:$blurred}';

  @override
  int get hashCode => hash([width, height, source, preview, blurred]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is PostImage &&
          runtimeType == other.runtimeType &&
          source == other.source &&
          preview == other.preview &&
          blurred == other.blurred &&
          width == other.width &&
          height == other.height;
}
