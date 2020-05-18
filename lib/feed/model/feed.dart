import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/object.dart';
import 'package:uuid/uuid.dart';

final _uuid = Uuid();

@immutable
class Feed {
  /// Unique ID to bind State with UI.
  final String id;

  /// Subreddit or general thread.
  final Type type;

  /// Sorting order.
  final Sort sort;

  Feed(this.type, this.sort)
      : id = _uuid.v1(),
        assert(type != null),
        assert(sort != null);

  factory Feed.fromJson(String raw) {
    if (raw?.isEmpty ?? true) return null;
    final json = jsonDecode(raw);
    return Feed(Type._(json['type']), Sort._(json['sort']));
  }

  String toJson() => '{ "type": "$type", "sort": "$sort" }';

  @override
  String toString() => '$type$sort';

  @override
  int get hashCode => hash([id, type, sort]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Feed &&
          runtimeType == other.runtimeType &&
          id == other.id &&
          type == other.type &&
          sort == other.sort;
}

/// Contains name of a subreddit.
@immutable
class Type {
  static const home = Type._('');
  static const popular = Type._('/r/popular');
  static const all = Type._('/r/all');

  static const Iterable<Type> values = [
    Type.home,
    Type.popular,
    Type.all,
  ];

  static Type subreddit(String path) => Type._('/r/$path');

  const Type._(this.path) : assert(path != null);

  final String path;

  String get label => this == Type.home ? '/home' : toString();

  Feed operator +(Sort sort) => Feed(this, sort);

  @override
  String toString() => path;

  @override
  int get hashCode => hash([path]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Type && runtimeType == other.runtimeType && path == other.path;
}

/// Contains sorting order.
@immutable
class Sort {
  static const best = Sort._('/best');
  static const hot = Sort._('/hot');
  static const newest = Sort._('/new');
  static const top = Sort._('/top');
  static const rising = Sort._('/rising');
  static const controversial = Sort._('/controversial');

  static const Iterable<Sort> values = [
    Sort.best,
    Sort.hot,
    Sort.newest,
    Sort.top,
    Sort.rising,
    //Sort.controversial,
  ];

  const Sort._(this.path);

  final path;

  String get label => toString();

  Feed operator +(Type type) => Feed(type, this);

  @override
  String toString() => path;

  @override
  int get hashCode => hash([path]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Type && runtimeType == other.runtimeType && path == other.path;
}
