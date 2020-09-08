import 'package:built_collection/built_collection.dart';
import 'package:built_value/built_value.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/state.dart';

part 'state.g.dart';

/// Top-level state representation.
abstract class AppState implements Built<AppState, AppStateBuilder> {
  static final AppState initial = AppState((b) => b
    ..status = Status.processing
    ..access = Access.unspecified
    ..lastFeed = Feed(FeedType.home, FeedSort.best)
    ..blurNsfw = false
    ..searchSuggestions.replace(FeedType.values)
    ..searchSort = FeedSort.best);

  /// Condition of this [AppState].
  Status get status;

  /// If [status] is [Status.failure] contains failure cause.
  @nullable
  Exception get exception;

  /// User's access status.
  Access get access;

  /// Last visible feed.
  ///
  /// The value is used to reopen on app start last feed user opened.
  Feed get lastFeed;

  /// If true NSFW-content will be blurred by default.
  bool get blurNsfw;

  /// Recently opened feed.
  BuiltMap<Feed, FeedState> get feeds;

  /// Search Screen suggestions.
  BuiltList<FeedType> get searchSuggestions;

  /// Search Screen sorting order.
  FeedSort get searchSort;

  AppState._();
  factory AppState([void Function(AppStateBuilder) updates]) = _$AppState;
}
