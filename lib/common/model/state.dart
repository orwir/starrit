import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/model/state.dart';
import 'package:starrit/common/config.dart';
import 'package:starrit/common/model/optional.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/search/model/state.dart';

/// Snapshot of the data used across the App.
@immutable
class AppState {
  static AppState initial = AppState(
    status: StateStatus.processing,
    access: Access.unspecified,
    latestFeed: Type.home + Sort.best,
    blurNsfw: false,
    feeds: const {},
    search: SearchState.initial,
  );

  /// Current status of this state.
  final StateStatus status;

  /// Access type.
  ///
  /// States:
  /// * [Access.unspecified] - a user hasn't made a decision yet or
  ///   app doesn't support authorization functionality -
  ///   [Config.supportAuthorization] == false.
  /// * [Access.anonymous] - a user made a decision to stay anonymous.
  /// * [Access.authorized] - a user passed authorization process
  ///   and has valid auth token.
  /// * [Access.revoked] - a user passed authorization but
  ///   for a reason nullified it.
  final Access access;

  /// Whether blur NSFW images and video previews.
  final bool blurNsfw;

  /// Feed from latest visible [FeedScreen].
  final Feed latestFeed;

  /// Contains cached states for currently opened/prefetched [FeedScreen]'s.
  final Map<Feed, FeedState> feeds;

  /// [SearchScreen] state.
  final SearchState search;

  /// Contains a token and/or temp data for an auth process. Non-null if:
  ///
  /// * [access] is [Access.authorized] or [Access.revoked].
  /// * An authorization process in progress.
  final AuthState auth;

  /// Cause of error during initialization.
  final Exception exception;

  AppState({
    @required this.status,
    @required this.access,
    @required this.latestFeed,
    @required this.blurNsfw,
    @required this.feeds,
    @required this.search,
    this.auth,
    this.exception,
  })  : assert(access != null),
        assert(latestFeed != null),
        assert(blurNsfw != null),
        assert(feeds != null),
        assert(search != null);

  AppState copyWith({
    StateStatus status,
    Access access,
    bool blurNsfw,
    Feed latestFeed,
    Map<Feed, FeedState> feeds,
    SearchState search,
    Optional<AuthState> auth,
    Optional<Exception> exception,
  }) =>
      AppState(
        status: status ?? this.status,
        access: access ?? this.access,
        blurNsfw: blurNsfw ?? this.blurNsfw,
        latestFeed: latestFeed ?? this.latestFeed,
        feeds: feeds ?? this.feeds,
        search: search ?? this.search,
        auth: auth != null ? auth.value : this.auth,
        exception: exception != null ? exception.value : this.exception,
      );

  @override
  String toString() =>
      '{ ' +
      [
        status == StateStatus.processing ? 'loading' : '',
        exception != null ? 'exception:$exception' : '',
        Config.supportAuthorization ? 'access:${access.label}' : '',
        auth != null ? 'auth:$auth' : '',
        blurNsfw ? 'blurNSFW' : '',
        latestFeed != null ? 'latestFeed:$latestFeed' : '',
        search != SearchState.initial ? 'search:$search' : '',
        'feeds:[${feeds.values.join(',')}]',
      ].where((line) => line.isNotEmpty).join(', ') +
      ' }';
}
