import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';

/// Request suggestion.
@immutable
class LoadSuggestions {
  /// keyword for lookup.
  final String query;

  LoadSuggestions(this.query) : assert(query != null);

  @override
  String toString() => '$runtimeType { $query }';
}

/// Successful response with relevant suggestions.
@immutable
class LoadSuggestionsSuccess {
  final List<Type> suggestions;

  LoadSuggestionsSuccess(this.suggestions) : assert(suggestions != null);

  @override
  String toString() => '$runtimeType { ${suggestions.length} }';
}

/// Unsuccessful reponse.
@immutable
class LoadSuggestionsFailure {
  final Exception exception;

  LoadSuggestionsFailure(this.exception) : assert(exception != null);

  @override
  String toString() => '$runtimeType { $exception }';
}

/// Update for sorting order.
@immutable
class UpdateSort {
  final Sort sort;

  UpdateSort(this.sort) : assert(sort != null);

  @override
  String toString() => '$runtimeType { $sort }';
}

/// Restores initial [SearchState].
@immutable
class DisposeSearchData {
  @override
  String toString() => '$runtimeType';
}
