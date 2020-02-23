import 'package:intl/intl.dart';

String get retry => Intl.message('retry');

String get now => Intl.message('now');

String hoursAgo(int hours) => Intl.message(
      '${hours}h ago',
      name: 'hoursAgo',
      args: [hours],
    );

String yesterdayAt(DateTime time) => Intl.message(
      'yesterday ${time.hour}:${time.minute}',
      name: 'yesterdayAt',
      args: [time],
    );
