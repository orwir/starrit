import 'package:flutter/foundation.dart';
import 'package:starrit/utils/object.dart';

import 'feed.dart';
import 'post.dart';

@immutable
class AppState {
  AppState({
    @required this.blurNsfw,
    @required this.search,
    @required this.feeds,
  });

  final bool blurNsfw;
  final SearchState search;
  final Map<Feed, FeedState> feeds;

  AppState copyWith({
    bool blurNfsw,
    SearchState search,
    Map<Feed, FeedState> feeds,
  }) =>
      AppState(
        blurNsfw: blurNfsw ?? this.blurNsfw,
        search: search ?? this.search,
        feeds: feeds ?? this.feeds,
      );

  FeedState operator [](Feed feed) => feeds != null ? feeds[feed] : null;

  @override
  String toString() => '{blurNsfw:$blurNsfw, search:$search, feeds:$feeds}';

  @override
  int get hashCode => hash([blurNsfw, search, feeds]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is AppState &&
          runtimeType == other.runtimeType &&
          blurNsfw == other.blurNsfw &&
          search == other.search &&
          feeds == other.feeds;
}

@immutable
class SearchState {
  SearchState({
    @required this.query,
    @required this.sort,
    @required this.suggestions,
  });

  SearchState.initial()
      : this(
          query: '',
          sort: Sort.best,
          suggestions: Type.values,
        );

  final String query;
  final Sort sort;
  final Iterable<Type> suggestions;

  SearchState copyWith({
    String query,
    Type type,
    Sort sort,
    Iterable<Type> suggestions,
  }) =>
      SearchState(
        query: query ?? this.query,
        sort: sort ?? this.sort,
        suggestions: suggestions ?? this.suggestions,
      );

  @override
  String toString() =>
      '{query:$query, sort:$sort, suggestions:${suggestions.length}}';

  @override
  int get hashCode => hash([query, sort, suggestions]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is SearchState &&
          runtimeType == other.runtimeType &&
          query == other.query &&
          sort == other.sort &&
          suggestions == other.suggestions;
}

@immutable
class FeedState {
  FeedState({
    @required this.feed,
    @required this.loading,
    @required this.exception,
    @required this.posts,
  });

  final Feed feed;
  final bool loading;
  final Exception exception;
  final List<Post> posts;

  FeedState copyWith({
    Feed feed,
    bool loading,
    Exception exception,
    List<Post> posts,
  }) =>
      FeedState(
        feed: feed ?? this.feed,
        loading: loading ?? this.loading,
        exception: exception ?? this.exception,
        posts: posts ?? this.posts,
      );

  @override
  String toString() =>
      '{loading:$loading, exception:${exception ?? ""}, posts:${posts.length}}';

  @override
  int get hashCode => hash([feed, loading, exception, posts]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedState &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          loading == other.loading &&
          exception == other.exception &&
          posts == other.posts;
}
