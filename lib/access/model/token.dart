import 'dart:convert';

import 'package:flutter/foundation.dart';

@immutable
class Token {
  static const Token none = Token._(
    access: '',
    type: '',
    expires: 0,
    scope: '',
    refresh: '',
    obtained: 0,
  );

  final String access;
  final String type;
  final int expires;
  final String scope;
  final String refresh;
  final int obtained;

  const Token._({
    @required this.access,
    @required this.type,
    @required this.expires,
    @required this.scope,
    @required this.refresh,
    @required this.obtained,
  })  : assert(access != null),
        assert(type != null),
        assert(expires != null),
        assert(scope != null),
        assert(refresh != null),
        assert(obtained != null);

  factory Token.fromJson(String raw) {
    if (raw?.isEmpty ?? true) return null;
    final json = jsonDecode(raw);
    return Token._(
      access: json['access'],
      type: json['type'],
      expires: json['expires'],
      scope: json['scope'],
      refresh: json['refresh'],
      obtained:
          json['obtained'] ?? DateTime.now().millisecondsSinceEpoch ~/ 1000,
    );
  }

  String toJson() => jsonEncode(this);

  bool get exipred =>
      DateTime.now().millisecondsSinceEpoch ~/ 1000 >=
      (obtained + expires - _safeThreshold);
}

const _safeThreshold = 5;
