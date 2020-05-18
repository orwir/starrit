import 'package:flutter/foundation.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';

@immutable
class SearchState {
  static const SearchState initial = SearchState(
    status: StateStatus.success,
    suggestions: Type.values,
    sort: Sort.best,
  );

  /// Current status of this state.
  final StateStatus status;

  /// Suggested subreddit by query.
  final List<Type> suggestions;

  /// Chosen sorting order.
  final Sort sort;

  /// Error during previous request.
  final Exception exception;

  const SearchState({
    @required this.status,
    @required this.suggestions,
    @required this.sort,
    this.exception,
  });

  @override
  String toString() =>
      '{ ' +
      [
        status == StateStatus.processing ? 'loading' : '',
        'suggestions:${suggestions.length}',
        'sort:$sort',
        exception == null ? '' : 'exception:$exception',
      ].where((line) => line.isNotEmpty).join(', ') +
      ' }';
}
