import 'package:flutter/foundation.dart';

@immutable
class Subreddit {
  final String name;
  final String icon;
  final String banner;

  Subreddit({@required this.name, @required this.icon, @required this.banner});

  @override
  String toString() => '{name: "$name", icon: "$icon", banner: "$banner"}';

  @override
  int get hashCode => name.hashCode ^ icon.hashCode ^ banner.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Subreddit &&
          runtimeType == other.runtimeType &&
          name == other.name &&
          icon == other.icon &&
          banner == other.banner;
}
