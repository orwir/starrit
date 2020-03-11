import 'package:flutter/foundation.dart';
import 'package:starrit/feed/models/feed.dart';
import 'package:starrit/feed/models/post.dart';
import 'package:starrit/utils/object.dart';

@immutable
class AppState {
  AppState({
    @required this.blurNsfw,
    @required this.search,
    @required this.feeds,
  })  : assert(blurNsfw != null),
        assert(feeds != null);

  final bool blurNsfw;
  final SearchState search;
  final Map<Feed, FeedState> feeds;

  AppState copyWith({
    bool blurNsfw,
    SearchState search,
    Map<Feed, FeedState> feeds,
  }) =>
      AppState(
        blurNsfw: blurNsfw ?? this.blurNsfw,
        search: search ?? this.search,
        feeds: feeds ?? this.feeds,
      );

  FeedState operator [](Feed feed) => feeds[feed];

  @override
  String toString() =>
      '{blurNsfw:$blurNsfw${search == SearchState.none ? "" : ", search:$search"}, feeds:$feeds}';

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
  static const SearchState none = SearchState(
    query: '[null]',
    sort: Sort.best,
    suggestions: const [],
  );

  const SearchState({
    @required this.query,
    @required this.sort,
    @required this.suggestions,
  })  : assert(query != null),
        assert(sort != null),
        assert(suggestions != null);

  final String query;
  final Sort sort;
  final List<Type> suggestions;

  SearchState copyWith({
    String query,
    Type type,
    Sort sort,
    List<Type> suggestions,
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
    @required this.posts,
    this.next,
    this.exception,
  })  : assert(feed != null),
        assert(loading != null),
        assert(posts != null);

  final Feed feed;
  final bool loading;
  final List<Post> posts;
  final String next;
  final Exception exception;

  FeedState toLoading({bool reset = false}) => FeedState(
        feed: feed,
        loading: true,
        posts: reset ? const [] : posts,
        next: reset ? null : next,
      );

  FeedState toSuccess(List<Post> posts, String next) => FeedState(
        feed: feed,
        loading: false,
        posts: this.posts + posts,
        next: next,
      );

  FeedState toFailure(Exception exception) => FeedState(
        feed: feed,
        loading: false,
        exception: exception,
        posts: posts,
        next: next,
      );

  @override
  String toString() =>
      '{loading:$loading${exception != null ? ", exception:$exception" : ""}, posts:${posts.length}, next:$next}';

  @override
  int get hashCode => hash([feed, loading, posts, next, exception]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedState &&
          runtimeType == other.runtimeType &&
          feed == other.feed &&
          loading == other.loading &&
          posts == other.posts &&
          next == other.next &&
          exception == other.exception;
}
