import 'package:flutter/foundation.dart';
import 'package:starrit/extensions/object.dart';

@immutable
class Author {
  final String id;
  final String name;

  Author({@required this.id, @required this.name});

  Author.fromJson(Map<String, dynamic> json)
      : this(
          id: json['author_fullname'],
          name: json['author'],
        );

  @override
  String toString() => '$runtimeType[id=$id, name=$name]';

  @override
  int get hashCode => hash([id, name]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Author &&
          runtimeType == other.runtimeType &&
          id == other.id &&
          name == other.name;
}
