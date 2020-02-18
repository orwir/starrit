import 'package:flutter/cupertino.dart';

@immutable
class Author {
  final String id;
  final String name;

  Author({@required this.id, @required this.name});

  @override
  String toString() => '{id: "$id", name: "$name"}';

  @override
  int get hashCode => id.hashCode ^ name.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Author &&
          runtimeType == other.runtimeType &&
          id == other.id &&
          name == other.name;
}
