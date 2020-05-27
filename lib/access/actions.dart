import 'package:flutter/foundation.dart';

@immutable
class SetAnonymousAccess {
  @override
  String toString() => '$runtimeType';
}

@immutable
class StartAuthorization {
  final String state;

  StartAuthorization(this.state);

  @override
  String toString() => '$runtimeType';
}

// @immutable
// class AccessUpdateSuccess {
//   @override
//   String toString() => '$runtimeType';
// }

// @immutable
// class AuthorizationSuccess {}

// @immutable
// class AuthorizationFailure {}
