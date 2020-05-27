import 'package:flutter/foundation.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/access/model/state.dart';
import 'package:starrit/feed/model/feed.dart';
import 'package:starrit/feed/model/state.dart';
import 'package:starrit/splash/model/initial.dart';

@immutable
class InitApplication {
  @override
  String toString() => '$runtimeType';
}

@immutable
class InitApplicationSuccess {
  final Initial _data;

  InitApplicationSuccess(this._data);

  Access get access => _data.access;
  AuthState get auth => _data.auth;
  bool get blurNsfw => _data.blurNsfw;
  Feed get feed => _data.feed.feed;
  FeedState get feedState => _data.feed;

  @override
  String toString() => '$runtimeType { $_data }';
}

@immutable
class InitApplicationFailure {
  final Exception exception;

  InitApplicationFailure(this.exception);

  @override
  String toString() => '$runtimeType { $exception }';
}
