import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';

@immutable
class UpdateBlurNsfw {
  final bool blurNsfw;

  UpdateBlurNsfw(this.blurNsfw);

  @override
  String toString() => '$runtimeType { $blurNsfw }';
}

@immutable
class UpdateLatestFeed {
  final Feed feed;

  UpdateLatestFeed(this.feed);

  @override
  String toString() => '$runtimeType { $feed }';
}
