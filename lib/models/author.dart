import 'package:flutter/foundation.dart';
import 'package:starrit/utils/object.dart';

@immutable
class Author {
  Author({@required this.id, @required this.name})
      : assert(id != null),
        assert(name != null);

  Author.fromJson(Map<String, dynamic> json)
      : this(
          id: json['author_fullname'],
          name: json['author'],
        );

  final String id;
  final String name;

  @override
  String toString() => '{id:$id, name:$name}';

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
