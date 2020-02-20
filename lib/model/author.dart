import 'package:flutter/cupertino.dart';

@immutable
class Author {
  final String id;
  final String name;

  Author({@required this.id, @required this.name});

  Author.fromJson(Map<String, dynamic> json)
      : this(
          id: json['author_fullname'] as String,
          name: json['author'] as String,
        );

  @override
  String toString() => '$runtimeType[id=$id, name=$name]';

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
