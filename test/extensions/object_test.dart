import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/extensions/object.dart';

void main() {
  group('Object.takeIf', () {
    test('should return 42 when predicate returns true', () {
      expect(42.takeIf((o) => true), 42);
    });
    test('should return null when predicate returns false', () {
      expect(42.takeIf((o) => false), null);
    });
  });
  group('Object.into', () {
    test('should return 42 when transformers return this value', () {
      expect('value'.into((o) => 42), 42);
    });
  });
  group('Object.hash', () {
    test(
      'should return 15312238088285232 when calls hash(["val", 5, 22.4])',
      () {
        expect('unused'.hash(['val', 5, 22.4]), 15312238088285232);
      },
    );
  });
}
