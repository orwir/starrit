import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/object.dart';

/// Container for posts images.
/// Keeps high & low res versions with the size of hi-rez image.
/// For nsfw/spoiler content contains blurred version.
@immutable
class PostImage {
  /// Width of hi-rez image.
  final int width;

  /// Height of hi-rez image.
  final int height;

  /// Hi-rez image.
  final String source;

  /// Low-rez image.
  final String preview;

  /// Blurred version of NSFW/spoiler image.
  final String blurred;

  PostImage({
    @required this.source,
    this.width,
    this.height,
    this.preview,
    this.blurred,
  }) : assert(source != null);

  bool get hasSize =>
      width != null && width > 0 && height != null && height > 0;

  @override
  String toString() =>
      '{ source:$source, size:${width}x$height, preview:$preview, blurred:$blurred }';

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
