import 'package:flutter/foundation.dart';
import 'package:starrit/util/json.dart';

@immutable
class Subreddit {
  final String name;
  final String icon;
  final String banner;

  Subreddit({@required this.name, @required this.icon, @required this.banner});

  Subreddit.fromJson(Map<String, dynamic> json)
      : this(
          name: json.path('sr_detail.display_name'),
          icon: json.nonEmpty('sr_detail.community_icon') ??
              json.nonEmpty('sr_detail.icon_img'),
          banner: json.nonEmpty('sr_detail.banner_img') ??
              json.nonEmpty('sr_detail.header_img'),
        );

  @override
  String toString() => '$runtimeType[name=$name, icon=$icon, banner=$banner]';

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
