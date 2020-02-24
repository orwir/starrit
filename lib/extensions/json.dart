import 'object.dart';

extension JsonMap on Map<String, dynamic> {
  String string(String key, {bool nonBlank = true}) {
    bool isNotBlank(String value) {
      return !nonBlank || value.trim().isNotEmpty;
    }

    return get<String>(key)?.takeIf(isNotBlank);
  }

  T get<T>(String key, {T def}) {
    Object element = this;
    for (var segment in key.split('.')) {
      var index = -1;
      if (segment[segment.length - 1] == ']') {
        index = int.parse(segment[segment.length - 2]);
        segment = segment.substring(0, segment.length - 3);
      }
      if (element is Map) {
        element = (element as Map)[segment];
      }
      if (index >= 0) {
        if (element is! List) {
          element = null;
          break;
        }
        element = (element as List)[index];
      }
      if (element == null) {
        break;
      }
    }
    return element as T ?? def;
  }
}
