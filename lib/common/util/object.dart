extension ChainObject on Object {
  /// If this value doesn't satisfy [predicate] return null.
  T takeIf<T>(Predicate<T> predicate) => predicate(this) ? this as T : null;

  /// Transforms this object into another by [transformer].
  R into<T, R>(Transformer<T, R> transformer) => transformer(this);
}

/// Makes hash of given [fields].
int hash(Iterable fields) {
  return fields.map((e) => e?.hashCode ?? 0).reduce((v, e) => v ^ e);
}

typedef Predicate<T> = bool Function(T value);

typedef Transformer<T, R> = R Function(T value);
