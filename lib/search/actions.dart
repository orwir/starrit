import 'package:flutter/foundation.dart';
import 'package:starrit/feed/models/feed.dart';
import 'package:starrit/common/utils/object.dart';

@immutable
class SearchDisposeAction {
  static const _instance = SearchDisposeAction._();

  factory SearchDisposeAction() => _instance;

  const SearchDisposeAction._();

  @override
  String toString() => '{type:$runtimeType}';
}

@immutable
class SearchSuggestionsRequestAction {
  SearchSuggestionsRequestAction(this.query) : assert(query != null);

  final String query;

  @override
  String toString() =>
      '{type:$runtimeType${query.isEmpty ? "" : ", query:$query"}}';

  @override
  int get hashCode => hash([query]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is SearchSuggestionsRequestAction &&
          runtimeType == other.runtimeType &&
          query == other.query;
}

@immutable
class SearchSuggestionsResponseAction {
  SearchSuggestionsResponseAction(this.query, this.suggestions)
      : assert(query != null),
        assert(suggestions != null);

  final String query;
  final List<Type> suggestions;

  @override
  String toString() =>
      '{type:$runtimeType, query:$query, suggestions:${suggestions.length}}';

  @override
  int get hashCode => hash([query, suggestions]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is SearchSuggestionsResponseAction &&
          runtimeType == other.runtimeType &&
          query == other.query &&
          suggestions == other.suggestions;
}

@immutable
class SearchSortChangeAction {
  SearchSortChangeAction(this.sort) : assert(sort != null);

  final Sort sort;

  @override
  String toString() => '{type:$runtimeType, sort:$sort}';

  @override
  int get hashCode => hash([sort]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is SearchSortChangeAction &&
          runtimeType == other.runtimeType &&
          sort == other.sort;
}
