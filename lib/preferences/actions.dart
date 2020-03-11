import 'package:flutter/foundation.dart';
import 'package:starrit/utils/object.dart';

@immutable
class BlurNsfwChangeAction {
  BlurNsfwChangeAction(this.blurNsfw) : assert(blurNsfw != null);

  final bool blurNsfw;

  @override
  String toString() => '{type:$runtimeType, blurNsfw:$blurNsfw}';

  @override
  int get hashCode => hash([blurNsfw]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is BlurNsfwChangeAction &&
          runtimeType == other.runtimeType &&
          blurNsfw == other.blurNsfw;
}
