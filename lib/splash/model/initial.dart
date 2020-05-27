import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/model/state.dart';
import 'package:starrit/feed/model/state.dart';

@immutable
class Initial {
  /// Access status.
  final Access access;

  /// Contains authorization token if user authorized.
  final AuthState auth;

  /// Whether show NSFW content blurred.
  final bool blurNsfw;

  /// State for first feed screen.
  final FeedState feed;

  Initial({
    @required this.access,
    @required this.auth,
    @required this.blurNsfw,
    @required this.feed,
  })  : assert(access != null),
        assert(blurNsfw != null),
        assert(feed != null);

  @override
  String toString() =>
      'access:$access, auth:$auth, blurNsfw:$blurNsfw, feed:$feed';
}
