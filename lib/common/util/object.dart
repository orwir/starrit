extension ChainObject on Object {
  T takeIf<T>(Function predicate) => predicate(this) ? this as T : null;

  T into<T>(Function transformer) => transformer(this);
}

int hash(Iterable fields) {
  return fields.map((e) => e?.hashCode ?? 0).reduce((v, e) => v ^ e);
}
