import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/common/util/object.dart';

void main() {
  test('takeIf should return the same value when predicate returns true', () {
    expect(42.takeIf((value) => true), 42);
  });

  test('takeIf should return null when predicate returns false', () {
    expect(42.takeIf((value) => false), isNull);
  });

  test('into(value) should transform object to another when invoked.', () {
    expect('value'.into((value) => 42), 42);
  });

  test(
    'hash(["val", 5, 22.4]) should return 15312238088285232 when invokes.',
    () {
      expect(hash(['val', 5, 22.4]), 15312238088285232);
    },
  );
}
