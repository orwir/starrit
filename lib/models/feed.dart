import 'package:flutter/foundation.dart';
import 'package:starrit/extensions/object.dart';

@immutable
class Feed {
  final String subreddit;
  final String sort;

  Feed(this.subreddit, this.sort);

  Feed.home() : this('', '/best');

  String get asParameter => '$subreddit$sort';

  String get asTitle => '${subreddit.isEmpty ? "/home" : subreddit}$sort';

  @override
  String toString() => '$runtimeType[${this.asParameter}]';

  @override
  int get hashCode => hash([subreddit, sort]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Feed &&
          runtimeType == other.runtimeType &&
          subreddit == other.subreddit &&
          sort == other.sort;
}
