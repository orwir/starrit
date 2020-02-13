import 'package:flutter/foundation.dart';

@immutable
class Subreddit {
  final String name;
  final String icon;
  final String banner;

  Subreddit({@required this.name, @required this.icon, @required this.banner});
}
