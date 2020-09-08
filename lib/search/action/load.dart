import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';

/// Request feed suggestions.
@immutable
class LoadSuggestions {
  /// Keyword to lookup.
  final String query;

  LoadSuggestions(this.query) : assert(query != null);

  @override
  String toString() => '$runtimeType { $query }';
}

/// Successful response for feed suggestions request.
@immutable
class LoadSuggestionsSuccess {
  /// List of suggestions based on keyword.
  final List<FeedType> suggestions;

  LoadSuggestionsSuccess(this.suggestions) : assert(suggestions != null);

  @override
  String toString() => '$runtimeType { ${suggestions.length} }';
}

/// Unsuccessful response for feed suggesions request.
@immutable
class LoadSuggestionsFailure {
  /// Error information.
  final Exception exception;

  LoadSuggestionsFailure(this.exception);

  @override
  String toString() => '$runtimeType { $exception }';
}
