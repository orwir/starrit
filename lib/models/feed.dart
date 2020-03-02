import 'package:flutter/foundation.dart';
import 'package:starrit/extensions/object.dart';

@immutable
class Feed {
  const Feed.home({Sort sort = Sort.best}) : this._(Type.home, sort);
  const Feed.popular({Sort sort = Sort.best}) : this._(Type.popular, sort);
  const Feed.all({Sort sort = Sort.best}) : this._(Type.all, sort);
  Feed.subreddit(String subreddit, {Sort sort = Sort.best})
      : this._(
          Type.subreddit(subreddit),
          sort,
        );
  const Feed._(this.type, this.sort);

  final Type type;
  final Sort sort;

  String get label => '${type.label}${sort.label}';

  @override
  String toString() => '$type$sort';

  @override
  int get hashCode => hash([type, sort]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Feed &&
          runtimeType == other.runtimeType &&
          type == other.type &&
          sort == other.sort;
}

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

  const Type.subreddit(this.path);
  const Type._(this.path);

  final String path;

  String get label => this == Type.home ? '/home' : toString();

  @override
  String toString() => path;

  @override
  int get hashCode => path.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Type && runtimeType == other.runtimeType && path == other.path;
}

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
    Sort.controversial,
  ];

  const Sort._(this.path);

  final path;

  String get label => toString();

  @override
  String toString() => path;

  @override
  int get hashCode => path.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Type && runtimeType == other.runtimeType && path == other.path;
}
