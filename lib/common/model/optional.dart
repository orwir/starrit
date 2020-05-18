import 'package:flutter/foundation.dart';

/// Wrapper for nullable values in States.
///
/// Useful for copyWith() methods.
@immutable
class Optional<T> {
  /// Wrapped value.
  final T value;

  Optional(this.value);
}

extension OptionalExtensions on Object {
  /// Wraps [Object] in [Optional].
  Optional get optional => Optional(this);
}
