import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/common/util/date.dart';

void main() {
  group('timeAgo', () {
    test(
      'should return "recently" when DateTime is less than hour to now.',
      () {
        final date = DateTime.now().subtract(Duration(minutes: 59));

        expect(date.timeAgo, 'recently');
      },
    );

    test('should return "2h ago" when DateTime is 2 hours ago to now.', () {
      final date = DateTime.now().subtract(Duration(hours: 2, minutes: 20));

      expect(date.timeAgo, '2h ago');
    });

    test('should return "yesterday at HH:mm" when DateTime suitable', () {
      final date = DateTime.now().subtract(Duration(days: 1));
      final hour = date.hour.toString().padLeft(2, '0');

      expect(date.timeAgo, 'yesterday at $hour:${date.minute}');
    });

    test(
      'should return date formatted "dd.MM HH:mm" when DateTime this year',
      () {
        final now = DateTime.now();
        final date = DateTime(now.year, 1, 12, 10, 40, 40);

        expect(date.timeAgo, '12.01 10:40');
      },
    );

    test(
      'should return date formatted "dd.MM.yyyy HH:mm" when DateTime more than year ago',
      () {
        final now = DateTime.now();
        final date = DateTime(now.year - 1, 1, 12, 10, 40, 40);

        expect(date.timeAgo, '12.01.${now.year - 1} 10:40');
      },
    );
  });
}
