import 'package:flutter/foundation.dart';
import 'package:starrit/feed/models/feed.dart';
import 'package:starrit/utils/object.dart';

@immutable
class SearchDisposeAction {
  static const _instance = SearchDisposeAction._();

  factory SearchDisposeAction() => _instance;

  const SearchDisposeAction._();

  @override
  String toString() => '{type:$runtimeType}';
}

@immutable
class SortUpdateAction {
  SortUpdateAction(this.sort) : assert(sort != null);

  final Sort sort;

  @override
  String toString() => '{type:$runtimeType, sort:$sort}';

  @override
  int get hashCode => hash([sort]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is SortUpdateAction &&
          runtimeType == other.runtimeType &&
          sort == other.sort;
}
