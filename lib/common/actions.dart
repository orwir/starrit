import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/model/state.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/state.dart';

@immutable
class InitApplication {
  @override
  String toString() => '$runtimeType';
}

@immutable
class InitApplicationSuccess {
  /// Access status.
  final Access access;

  /// If authorized contains token.
  final AuthState auth;

  /// Whether show NSFW content blurred.
  final bool blurNsfw;

  /// Latest visible feed.
  final Feed feed;

  /// Prefetched latest feed data.
  final FeedState feedState;

  InitApplicationSuccess({
    this.access,
    this.auth,
    this.blurNsfw,
    this.feed,
    this.feedState,
  })  : assert(feed != null),
        assert(blurNsfw != null),
        assert(access != null),
        assert(feedState != null);

  @override
  String toString() => '$runtimeType';
}

@immutable
class InitApplicationFailure {
  final Exception exception;

  InitApplicationFailure(this.exception) : assert(exception != null);

  @override
  String toString() => '$runtimeType';
}
