extension ChainObject on Object {
  T takeIf<T>(Function predicate) => predicate(this) ? this as T : null;
  R into<T, R>(Function transformer) => transformer(T);
}
