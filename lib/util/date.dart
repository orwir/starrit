import 'package:intl/intl.dart';
import 'package:starrit/i18n/text.dart' as T;

extension TimeAgo on DateTime {
  String asTimeAgo() {
    final now = DateTime.now();
    final diff = now.difference(this);
    if (diff.inHours < 1) {
      return T.now;
    }
    if (diff.inDays < 1) {
      return T.hoursAgo(diff.inHours);
    }
    if (diff.inDays == 1) {
      return T.yesterdayAt(this);
    }
    if (year == now.year) {
      final thisYear = DateFormat('dd.MM HH:mm');
      return thisYear.format(this);
    }
    final full = DateFormat('dd.MM.yyyy HH:mm');
    return full.format(this);
  }
}
