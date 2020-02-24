extension ChainObject on Object {
  T takeIf<T>(Function predicate) => predicate(this) ? this as T : null;
  T into<T>(Function transformer) => transformer(this);
}
