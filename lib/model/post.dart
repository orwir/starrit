import 'package:flutter/foundation.dart';
import 'package:starrit/model/author.dart';
import 'package:starrit/model/image.dart';
import 'package:starrit/model/subreddit.dart';

@immutable
class Post {
  final String id;
  final Subreddit subreddit;
  final Author author;
  final int created;
  final String title;
  final bool nsfw;
  final bool spoiler;
  final int comments;
  final int score;
  final String domain;
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
    @required this.postUrl,
    @required this.contentUrl,
    @required this.imagePreview,
    @required this.imageSource,
    @required this.imageBlurred,
  });

  Post.fromJson(Map<String, dynamic> json)
      : this(
          id: json['name'] as String,
          subreddit: null, //todo: implement it
          author: null, //todo: implement it
          created: (json['created_utc'] as double).toInt(),
          title: json['title'] as String,
          nsfw: json['nsfw'] as bool,
          spoiler: json['spoiler'] as bool,
          comments: json['num_comments'] as int,
          score: json['score'] as int,
          domain: json['domain'] as String,
          postUrl: json['permalink'] as String,
          contentUrl: json['url'] as String,
          imagePreview: null, //todo: implement it
          imageSource: null, //todo: implement it
          imageBlurred: null, //todo: implement it
        );

  @override
  int get hashCode =>
      id.hashCode ^
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
      imagePreview.hashCode ^
      imageSource.hashCode ^
      imageBlurred.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
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
