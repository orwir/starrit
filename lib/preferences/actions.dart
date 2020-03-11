import 'package:flutter/foundation.dart';

@immutable
class BlurNsfwChangeAction {
  BlurNsfwChangeAction(this.blurNsfw);

  final bool blurNsfw;

  @override
  String toString() => '{type:$runtimeType, blurNsfw:$blurNsfw}';
}
