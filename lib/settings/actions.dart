import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/feed/model/feed.dart';

@immutable
class LoadPreferencesSuccess {
  final Feed latestFeed;
  final bool blurNsfw;
  final Access access;

  LoadPreferencesSuccess({this.latestFeed, this.blurNsfw, this.access});

  @override
  String toString() =>
      '$runtimeType { lastestFeed:$latestFeed, blurNsfw:$blurNsfw, access:$access }';
}

@immutable
class LoadPreferencesFailure {
  final Exception exception;

  LoadPreferencesFailure(this.exception);

  @override
  String toString() => '$runtimeType { $exception }';
}

@immutable
class UpdatePreference {
  final bool blurNsfw;
  final Feed latestFeed;

  UpdatePreference({this.blurNsfw, this.latestFeed});

  @override
  String toString() =>
      '$runtimeType { ' +
      [
        blurNsfw != null ? 'blurNsfw:$blurNsfw' : '',
        latestFeed != null ? 'latestFeed:$latestFeed' : '',
      ].where((line) => line.isNotEmpty).join(', ') +
      ' }';
}

class UpdatePreferenceSuccess {
  final bool blurNsfw;
  final Feed latestFeed;

  UpdatePreferenceSuccess({this.blurNsfw, this.latestFeed});

  @override
  String toString() =>
      '$runtimeType { ' +
      [
        blurNsfw != null ? 'blurNsfw:$blurNsfw' : '',
        latestFeed != null ? 'latestFeed:$latestFeed' : '',
      ].where((line) => line.isNotEmpty).join(', ') +
      ' }';
}

class UpdatePreferenceFailure {
  final Exception exception;

  UpdatePreferenceFailure(this.exception) : assert(exception != null);

  @override
  String toString() => '$runtimeType { $exception }';
}
