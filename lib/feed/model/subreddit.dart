import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/common/util/object.dart';

/// Information about subreddit.
@immutable
class Subreddit {
  /// Name of a subreddit.
  final String name;

  /// Icon url.
  final String icon;

  /// Banner url.
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
  String toString() => '{ name:$name, icon:$icon, banner:$banner }';

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
