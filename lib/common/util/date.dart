import 'package:intl/intl.dart';

final _thisYear = DateFormat('dd.MM HH:mm');
final _fullDate = DateFormat('dd.MM.yyyy HH:mm');

extension TimeAgo on DateTime {
  /// Formats [DateTime] as 'time ago' to current time point.
  ///
  ///
  /// Format rules:
  /// * less than 1 hour -> recently
  /// * less than 1 day -> #h ago
  /// * yesterday -> yesterday
  /// * this year -> dd.MM HH:mm
  /// * otherwise -> dd.MM.yyyy HH:mm
  String get timeAgo {
    final now = DateTime.now();
    final diff = now.difference(this);

    if (diff.inHours < 1) return 'recently';

    if (diff.inDays < 1) return '${diff.inHours}h ago';

    if (diff.inDays == 1) {
      return 'yesterday at ${hour.toString().padLeft(2, '0')}:$minute';
    }

    if (year == now.year) return _thisYear.format(this);

    return _fullDate.format(this);
  }
}
