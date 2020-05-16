import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/config.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/search/model/state.dart';

/// Mega-snapshot of App data.
@immutable
class AppState {
  /// Status of global data like preferences.
  final StateStatus status;

  /// Access state
  final Access access;

  /// Latest visible feed to start with on app launch.
  final Feed latestFeed;

  /// Whether show NSFW content blurred.
  final bool blurNsfw;

  /// Data storage for currently opened Feed Screens.
  final Map<Feed, FeedState> feeds;

  /// Search data state.
  final SearchState search;

  AppState({
    @required this.status,
    @required this.access,
    @required this.feeds,
    @required this.search,
    this.latestFeed,
    this.blurNsfw,
  })  : assert(status != null),
        assert(access != null),
        assert(feeds != null),
        assert(blurNsfw != null);

  AppState.initial()
      : this(
          status: StateStatus.initial,
          access: Access.unspecified,
          search: SearchState.initial,
          feeds: const {},
          blurNsfw: false,
        );

  AppState copyWith({
    StateStatus status,
    Access access,
    Map<Feed, FeedState> feeds,
    SearchState search,
    Feed latestFeed,
    bool blurNsfw,
  }) =>
      AppState(
        status: status ?? this.status,
        access: access ?? this.access,
        feeds: feeds ?? this.feeds,
        latestFeed: latestFeed ?? this.latestFeed,
        blurNsfw: blurNsfw ?? this.blurNsfw,
        search: search ?? this.search,
      );

  @override
  String toString() =>
      '{ ' +
      [
        status == StateStatus.loading ? 'loading' : '',
        Config.hasAccessMode ? 'access:${access.label}' : '',
        blurNsfw ? 'blurNSFW' : '',
        latestFeed != null ? 'latestFeed:$latestFeed' : '',
        search != SearchState.initial ? 'search:$search' : '',
        'feeds:[${feeds.values.join(',')}]',
      ].where((line) => line.isNotEmpty).join(', ') +
      ' }';
}
