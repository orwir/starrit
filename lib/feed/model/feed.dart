import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:uuid/uuid.dart';

/// Generator of unqiue ids to separate similar feeds.
final _uuid = Uuid();

/// Feed representation.
@immutable
class Feed {
  /// Metadata ID to make Feed unique even if type and sort are the same.
  final String _id;

  /// Type of the feed (home, all, subreddit, etc).
  final FeedType type;

  /// Sort order of the feed.
  final FeedSort sort;

  Feed(this.type, this.sort)
      : assert(type != null),
        assert(sort != null),
        _id = _uuid.v1();

  factory Feed.fromJson(String raw) {
    if (raw?.isEmpty ?? true) return null;
    final json = jsonDecode(raw);
    return Feed(
      FeedType._fromJson(json['type']),
      FeedSort._(json['sort']),
    );
  }

  String toJson() => '{ "type": "${type.path}", "sort": "${sort.path}" }';

  String get path => '${type.path}${sort.path}';

  @override
  String toString() => '$type$sort';

  @override
  int get hashCode => hash([_id, type, sort]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Feed &&
          runtimeType == other.runtimeType &&
          _id == other._id &&
          type == other.type &&
          sort == other.sort;
}

/// Enum-like wrapper representation of path part of the subreddit for [Feed].
@immutable
class FeedType {
  /// Main page. Exactly as open reddit.com
  static const home = FeedType._('', '/home');

  /// Popular subreddit.
  static const popular = FeedType._('/r/popular');

  /// 'All' subreddit.
  static const all = FeedType._('/r/all');

  /// Specified by [path] subreddit.
  static FeedType subreddit(String path) => FeedType._('/r/$path');

  /// Enum-like collection of predefined subreddits.
  static const Iterable<FeedType> values = [
    FeedType.home,
    FeedType.popular,
    FeedType.all,
  ];

  /// Path part of a URI to get feed data.
  final String path;

  /// Text to display on UI.
  final String label;

  const FeedType._(this.path, [String label])
      : assert(path != null),
        this.label = label ?? path;

  factory FeedType._fromJson(String raw) {
    assert(raw != null);
    for (final type in FeedType.values) {
      if (type.path == raw) return type;
    }
    return FeedType._(raw);
  }

  @override
  String toString() => label;

  @override
  int get hashCode => hash([path]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedType &&
          runtimeType == other.runtimeType &&
          path == other.path;
}

/// Enum-like representation of sorting order for [Feed].
@immutable
class FeedSort {
  static const best = FeedSort._('/best');
  static const hot = FeedSort._('/hot');
  static const newest = FeedSort._('/new');
  static const top = FeedSort._('/top');
  static const rising = FeedSort._('/rising');
  static const controversial = FeedSort._('/controversial');

  /// Enum-like collection of predefined sort orders.
  static const Iterable<FeedSort> values = [
    FeedSort.best,
    FeedSort.hot,
    FeedSort.newest,
    FeedSort.top,
    FeedSort.rising,
    //Sort.controversial,
  ];

  /// Path part of a URI to get feed data.
  final String path;

  const FeedSort._(this.path);

  /// Text to display on UI.
  String get label => path;

  @override
  String toString() => label;

  @override
  int get hashCode => hash([path]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is FeedSort &&
          runtimeType == other.runtimeType &&
          path == other.path;
}

/// A single page of feed data.
@immutable
class FeedChunk {
  /// Posts.
  final List<Post> posts;

  /// ID to request next chunk of data.
  final String next;

  FeedChunk(this.posts, this.next);
}
