extension JsonMap on Map<String, dynamic> {
  T get<T>(String key) => this[key] as T;

  T path<T>(String path, {T def}) {
    Object element = this;
    for (var segment in path.split('.')) {
      var index = -1;
      if (segment[segment.length - 1] == ']') {
        index = int.parse(segment[segment.length - 2]);
        segment = segment.substring(0, segment.length - 3);
      }
      element = (element as Map)[segment];
      if (index >= 0) {
        if (element is List) {
          element = (element as List)[index];
        } else {
          element = null;
          break;
        }
      }
      if (element == null) {
        break;
      }
    }
    return element as T ?? def;
  }
}
