import 'package:flutter/foundation.dart';

@immutable
class Feed {
  final String subreddit;
  final String sort;

  Feed({this.subreddit, this.sort});

  Feed.home() : this(subreddit: '', sort: '/best');

  String get asParameter => '$subreddit$sort';

  String get asTitle => '${subreddit.isEmpty ? "/home" : subreddit}$sort';

  @override
  String toString() => '{subreddit: "$subreddit", sort: "$sort"}';

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Feed &&
          this.runtimeType == other.runtimeType &&
          subreddit == other.subreddit &&
          sort == other.sort;

  @override
  int get hashCode => subreddit.hashCode ^ sort.hashCode;
}
