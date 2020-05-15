import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';

/// Load feed path suggestions by query.
/// If query length is less than 3 default (general) paths will be returned.
@immutable
class LoadSuggestions {
  final String query;

  LoadSuggestions(this.query) : assert(query != null);

  @override
  String toString() => '$runtimeType { $query }';
}

/// Successful response with a piece of requested feed data.
@immutable
class LoadSuggestionsSuccess {
  final List<Type> suggestions;

  LoadSuggestionsSuccess(this.suggestions) : assert(suggestions != null);

  @override
  String toString() => '$runtimeType { ${suggestions.length} }';
}

/// Unsuccessful reponse with the cause of an error.
@immutable
class LoadSuggestionsFailure {
  final Exception exception;

  LoadSuggestionsFailure(this.exception) : assert(exception != null);

  @override
  String toString() => '$runtimeType { $exception }';
}

/// Update sorting order.
@immutable
class UpdateSort {
  final Sort sort;

  UpdateSort(this.sort) : assert(sort != null);

  @override
  String toString() => '$runtimeType { $sort }';
}

/// Request to delete data from the storage.
/// Occurs when Feed Screen is closed.
@immutable
class DisposeSearchData {
  @override
  String toString() => '$runtimeType';
}
