import 'package:intl/intl.dart';

extension TimeAgo on DateTime {
  String asTimeAgo() {
    final now = DateTime.now();
    final diff = now.difference(this);
    if (diff.inHours < 1) {
      return 'now';
    }
    if (diff.inDays < 1) {
      return '${diff.inHours}h ago';
    }
    if (diff.inDays == 1) {
      return 'yesterday $hour:$minute';
    }
    if (year == now.year) {
      final thisYear = DateFormat('dd.MM HH:mm');
      return thisYear.format(this);
    }
    final full = DateFormat('dd.MM.yyyy HH:mm');
    return full.format(this);
  }
}
