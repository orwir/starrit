import 'package:flutter/foundation.dart';
import 'package:starrit/model/author.dart';
import 'package:starrit/model/image.dart';
import 'package:starrit/model/subreddit.dart';

@immutable
class Post {
  final String id;
  final Subreddit subreddit;
  final Author author;
  final String created;
  final String title;
  final bool nsfw;
  final bool spoiler;
  final String comments;
  final String score;
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
          subreddit: Subreddit.fromJson(json),
          author: Author.fromJson(json),
          created: _prettyCreatedDate((json['created_utc'] as double).toInt()),
          title: json['title'] as String,
          nsfw: json['nsfw'] as bool,
          spoiler: json['spoiler'] as bool,
          comments: _prettyNumber(json['num_comments'] as int),
          score: _prettyNumber(json['score'] as int),
          domain: json['domain'] as String,
          postUrl: json['permalink'] as String,
          contentUrl: json['url'] as String,
          imagePreview: _findImagePreview(json),
          imageSource: _findImageSource(json),
          imageBlurred: _findImageBlurred(json),
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
      (imagePreview?.hashCode ?? 0) ^
      (imageSource?.hashCode ?? 0) ^
      (imageBlurred?.hashCode ?? 0);

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

String _prettyCreatedDate(int ms) {
  //TODO: implement it
  return ms.toString();
}

String _prettyNumber(int number) {
  //TODO: implement it
  return number.toString();
}

ImageData _findImagePreview(Map<String, dynamic> json) {
  //TODO: implement it
  /*
  submission.preview
        ?.images
        ?.firstOrNull()
        ?.resolutions
        ?.firstOrNull()
        ?: Image("", 0, 0)
  */
  return null;
}

ImageData _findImageSource(Map<String, dynamic> json) {
  //TODO: implement it
  /*
  submission.preview
        ?.images
        ?.firstOrNull()
        ?.source
        ?: Image("", 0, 0)

  */
  return null;
}

ImageData _findImageBlurred(Map<String, dynamic> json) {
  //TODO: implement it
  /*
  submission
        .preview
        ?.images
        ?.firstOrNull()
        ?.variants
        ?.nsfw
        ?.resolutions
        ?.firstOrNull()
        ?: Image("", 0, 0)

  */
  return null;
}
