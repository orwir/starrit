import 'package:intl/intl.dart';

extension TimeAgo on DateTime {
  String get asTimeAgo {
    final now = DateTime.now();
    final diff = now.difference(this);

    if (diff.inHours < 1) {
      return 'recently';
    }
    if (diff.inDays < 1) {
      return '${diff.inHours}h ago';
    }
    if (diff.inDays == 1) {
      return 'yesterday at $hour:$minute';
    }
    if (year == now.year) {
      return _thisYear.format(this);
    }
    return _fullDate.format(this);
  }
}

final _thisYear = DateFormat('dd.MM HH:mm');
final _fullDate = DateFormat('dd.MM.yyyy HH:mm');
