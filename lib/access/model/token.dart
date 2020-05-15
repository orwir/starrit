import 'dart:convert';

import 'package:flutter/foundation.dart';

@immutable
class Token {
  final String access;
  final String type;
  final int expires;
  final String scope;
  final String refresh;
  final int obtained;

  Token({
    @required this.access,
    @required this.type,
    @required this.expires,
    @required this.scope,
    @required this.refresh,
    String obtained,
  })  : this.obtained =
            obtained ?? DateTime.now().millisecondsSinceEpoch ~/ 1000,
        assert(access != null),
        assert(type != null),
        assert(expires != null),
        assert(scope != null),
        assert(refresh != null);

  factory Token.fromJson(String raw) {
    if (raw?.isEmpty ?? true) return null;
    final json = jsonDecode(raw);
    return Token(
      access: json['access'],
      type: json['type'],
      expires: json['expires'],
      scope: json['scope'],
      refresh: json['refresh'],
      obtained: json['obtained'],
    );
  }

  String toJson() => jsonEncode(this);

  bool get exipred =>
      DateTime.now().millisecondsSinceEpoch ~/ 1000 >=
      (obtained + expires - _safeThreshold);
}

const _safeThreshold = 5;
