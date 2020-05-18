import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/object.dart';

/// Post author.
@immutable
class Author {
  /// Account ID (fullname).
  ///
  /// Null means account was deleted.
  final String id;

  /// Displayed name.
  final String name;

  Author(this.id, this.name) : assert(name != null);

  Author.fromJson(Map<String, dynamic> json)
      : this(json['author_fullname'], json['author']);

  bool get deleted => id == null;

  @override
  String toString() => deleted ? '{ deleted }' : '{ id:$id, name:$name }';

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
