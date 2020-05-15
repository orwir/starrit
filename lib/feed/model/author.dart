import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/object.dart';

/// Represents author of a post.
@immutable
class Author {
  /// User account ID. (fullname)
  /// Null means post's author was deleted.
  final String id;

  /// User name.
  final String name;

  Author(this.id, this.name) : assert(name != null);

  Author.fromJson(Map<String, dynamic> json)
      : this(json['author_fullname'], json['author']);

  bool get deleted => id == null;

  @override
  String toString() => '{ id:$id, name:$name }';

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
