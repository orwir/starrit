import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/object.dart';

/// Post images container.
///
/// Keeps [preview], [source], [blurred] version as well a size of a hi-rez image.
@immutable
class PostImage {
  /// Source image width.
  final int width;

  /// Source image height.
  final int height;

  /// Original (hi-rez) image.
  final String source;

  /// Low-rez image.
  final String preview;

  /// Blurred image.
  ///
  /// Non-null if the post marked as NSFW/SPOILER.
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

  double get ratio => hasSize ? width / height : 1;

  @override
  String toString() =>
      '{ source:$source${hasSize ? ', size:${width}x$height' : ''}, '
      'preview:$preview, '
      'blurred:$blurred }';

  @override
  int get hashCode => hash([source, width, height, preview, blurred]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is PostImage &&
          runtimeType == other.runtimeType &&
          source == other.source &&
          width == other.width &&
          height == other.height &&
          preview == other.preview &&
          blurred == other.blurred;
}
