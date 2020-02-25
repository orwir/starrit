import 'package:flutter/foundation.dart';
import 'package:starrit/extensions/json.dart';
import 'package:starrit/extensions/object.dart';

@immutable
class Subreddit {
  final String name;
  final String icon;
  final String banner;

  Subreddit({@required this.name, @required this.icon, @required this.banner});

  Subreddit.fromJson(Map<String, dynamic> json)
      : this(
          name: json.string('sr_detail.display_name'),
          icon: json.string('sr_detail.community_icon') ??
              json.string('sr_detail.icon_img'),
          banner: json.string('sr_detail.banner_img') ??
              json.string('sr_detail.header_img'),
        );

  @override
  String toString() => '$runtimeType[name=$name, icon=$icon, banner=$banner]';

  @override
  int get hashCode => hash([name, icon, banner]);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Subreddit &&
          runtimeType == other.runtimeType &&
          name == other.name &&
          icon == other.icon &&
          banner == other.banner;
}
