import 'package:flutter/foundation.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/model/feed.dart';

@immutable
class SearchState {
  static const SearchState initial = SearchState(
    status: StateStatus.initial,
    suggestions: const [],
    sort: Sort.best,
  );

  final StateStatus status;
  final List<Type> suggestions;
  final Sort sort;
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
        status == StateStatus.loading ? 'loading' : '',
        'suggestions:${suggestions.length}',
        'sort:$sort',
        exception == null ? '' : 'exception:$exception',
      ].where((line) => line.isNotEmpty).join(', ') +
      ' }';
}
