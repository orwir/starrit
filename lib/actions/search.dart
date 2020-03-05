import 'package:flutter/foundation.dart';
import 'package:starrit/models/feed.dart';

@immutable
class SearchDisposeAction {
  SearchDisposeAction();

  @override
  String toString() => '{type:$runtimeType}';
}

@immutable
class SortUpdateAction {
  SortUpdateAction(this.sort);

  final Sort sort;

  @override
  String toString() => '{type:$runtimeType, sort:$sort}';
}
