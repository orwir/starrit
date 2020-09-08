import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';

/// Update sort order on a Search Screen.
@immutable
class UpdateSort {
  /// New value for sort order.
  final FeedSort sort;

  UpdateSort(this.sort);

  @override
  String toString() => '$runtimeType { $sort }';
}
