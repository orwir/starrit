extension ChainObject on Object {
  /// Returns this value if a [predicate] is true.
  ///
  /// Otherwise returns null.
  T takeIf<T>(Predicate<T> predicate) => predicate(this) ? this as T : null;

  /// Transforms this object into another by a [transformer].
  R into<T, R>(Transformer<T, R> transformer) => transformer(this);
}

/// Calculates a hash of given [values].
int hash(Iterable values) {
  return values.map((e) => e?.hashCode ?? 0).reduce((v, e) => v ^ e);
}

typedef Predicate<T> = bool Function(T value);

typedef Transformer<T, R> = R Function(T value);
