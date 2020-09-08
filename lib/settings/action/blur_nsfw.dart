import 'package:flutter/foundation.dart';

/// Toggle-action to on/off blur on NSFW content.
@immutable
class BlurNsfw {
  /// Blur if true, orginal image otherwise.
  final bool enabled;

  BlurNsfw(this.enabled);

  @override
  String toString() => '$runtimeType { $enabled }';
}
