import 'package:flutter/foundation.dart';
import 'package:starrit/model/author.dart';
import 'package:starrit/model/image.dart';
import 'package:starrit/model/subreddit.dart';
import 'package:starrit/util/json.dart';

@immutable
class Post {
  final String id;
  final Subreddit subreddit;
  final Author author;
  final DateTime created;
  final String title;
  final bool nsfw;
  final bool spoiler;
  final int comments;
  final int score;
  final String domain;
  final bool selfDomain;
  final String postUrl;
  final String contentUrl;
  final ImageData imagePreview;
  final ImageData imageSource;
  final ImageData imageBlurred;

  Post({
    @required this.id,
    @required this.subreddit,
    @required this.author,
    @required this.created,
    @required this.title,
    @required this.nsfw,
    @required this.spoiler,
    @required this.comments,
    @required this.score,
    @required this.domain,
    @required this.selfDomain,
    @required this.postUrl,
    @required this.contentUrl,
    @required this.imagePreview,
    @required this.imageSource,
    @required this.imageBlurred,
  });

  Post.fromJson(Map<String, dynamic> json)
      : this(
          id: json['name'] as String,
          subreddit: Subreddit.fromJson(json),
          author: Author.fromJson(json),
          created: DateTime.fromMillisecondsSinceEpoch(
            (json['created_utc'] as double).toInt() * 1000,
            isUtc: true,
          ),
          title: json['title'] as String,
          nsfw: json['nsfw'] as bool ?? false,
          spoiler: json['spoiler'] as bool ?? false,
          comments: json['num_comments'] as int,
          score: json['score'] as int,
          domain: json['domain'] as String,
          selfDomain: (json['domain'] as String).startsWith('self.'),
          postUrl: json['permalink'] as String,
          contentUrl: json['url'] as String,
          imagePreview: _image(json, 'preview.images[0].resolutions[0]'),
          imageSource: _image(json, 'preview.images[0].source'),
          imageBlurred: _image(
            json,
            'preview.images[0].variants.nsfw.resolutions[0]',
          ),
        );

  @override
  int get hashCode {
    return id.hashCode ^
        subreddit.hashCode ^
        author.hashCode ^
        created.hashCode ^
        title.hashCode ^
        nsfw.hashCode ^
        spoiler.hashCode ^
        comments.hashCode ^
        domain.hashCode ^
        postUrl.hashCode ^
        contentUrl.hashCode ^
        (imagePreview?.hashCode ?? 0) ^
        (imageSource?.hashCode ?? 0) ^
        (imageBlurred?.hashCode ?? 0);
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        other is Post &&
            runtimeType == other.runtimeType &&
            id == other.id &&
            subreddit == other.subreddit &&
            author == other.author &&
            created == other.created &&
            title == other.title &&
            nsfw == other.nsfw &&
            spoiler == other.spoiler &&
            comments == other.comments &&
            domain == other.domain &&
            postUrl == other.postUrl &&
            contentUrl == other.contentUrl &&
            imagePreview == other.imagePreview &&
            imageSource == other.imageSource &&
            imageBlurred == other.imageBlurred;
  }
}

ImageData _image(Map<String, dynamic> json, String path) {
  final jsonImage = json.path(path);
  return jsonImage != null ? ImageData.fromJson(jsonImage) : null;
}
