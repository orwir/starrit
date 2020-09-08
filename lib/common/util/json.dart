import 'package:starrit/common/util/object.dart';

extension JsonMap on Map<String, dynamic> {
  /// Obtains string value by [path].
  ///
  /// Returns null if value not found or it doesn't contains any 'word char'
  /// and [nonBlank] is true.
  String string(String key, {bool nonBlank = true}) {
    bool isNotBlank(String value) {
      return !nonBlank || value.trim().isNotEmpty;
    }

    return get<String>(key)?.takeIf(isNotBlank);
  }

  /// Obtains value from json by path.
  ///
  /// [path] - sequence of keys separated by dot.
  /// Can access arrays with [#] syntax.
  /// E.q.: `preview.images[0].resolutions[0]`
  ///
  /// [def] - optional default value if value is not present.
  T get<T>(String path, {T def}) {
    Object element = this;
    for (var segment in path.split('.')) {
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
