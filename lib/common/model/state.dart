import 'package:flutter/foundation.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/search/model/state.dart';

/// Mega-snapshot of App data.
@immutable
class AppState {
  /// Status of global data like preferences.
  final StateStatus status;

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
    @required this.feeds,
    this.search,
    this.latestFeed,
    this.blurNsfw,
  })  : assert(status != null),
        assert(feeds != null),
        assert(blurNsfw != null);

  AppState.initial()
      : this(
          status: StateStatus.initial,
          feeds: const {},
          search: SearchState.initial,
          blurNsfw: false,
        );

  AppState copyWith({
    StateStatus status,
    Map<Feed, FeedState> feeds,
    SearchState search,
    Feed latestFeed,
    bool blurNsfw,
  }) =>
      AppState(
        status: status ?? this.status,
        feeds: feeds ?? this.feeds,
        search: search ?? this.search,
        latestFeed: latestFeed ?? this.latestFeed,
        blurNsfw: blurNsfw ?? this.blurNsfw,
      );

  @override
  String toString() => '{ '
      'feeds:[${feeds.values.join(',')}]'
      '${latestFeed != null ? ', latestFeed:$latestFeed' : ''}'
      '${blurNsfw ? ', blurNSFW' : ''}'
      '${status == StateStatus.loading ? ', loading' : ''}'
      '${search != SearchState.initial ? ', search:$search' : ''}'
      ' }';
}

/// Determine status of specific state.
enum StateStatus {
  /// Data has never requested.
  initial,

  /// Started process of data obtaining.
  loading,

  /// Data successfully obtained.
  success,

  /// Data obtain completed with an error.
  failure,
}

extension StateStatusExtension on StateStatus {
  /// As long as toString() method for enums not nice this isa workaround.
  String get label => toString().split('.')[1];
}
