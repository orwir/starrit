import 'package:starrit/common/util/object.dart';

extension JsonMap on Map<String, dynamic> {
  /// Obtains a string value from this json by [path].
  ///
  /// Returns null if a value not found or a value has only "spaces"
  /// and [nonBlank] is true.
  String string(String path, {bool nonBlank = true}) {
    bool isNotBlank(String value) {
      return !nonBlank || value.trim().isNotEmpty;
    }

    return get<String>(path)?.takeIf(isNotBlank);
  }

  /// Obtains a value from this json by [path].
  ///
  /// [path] - a sequence of keys separated by dots.
  ///
  /// To access a specific elements in an arrays uses a syntax similar to
  /// dart's one. E.q.: `preview.images[0].resolutions[0]`
  ///
  /// [def] - an optional default value if a value by [path] does not found.
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
