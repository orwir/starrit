import 'package:flutter/foundation.dart';
import 'package:starrit/model/author.dart';
import 'package:starrit/model/image.dart';
import 'package:starrit/model/subreddit.dart';
import 'package:starrit/util/json.dart';
import 'package:starrit/util/content.dart';

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
  final ContentType type;
  final ImageData imagePreview;
  final ImageData imageSource;
  final ImageData imageBlurred;
  final String text;
  final String gif;
  final String video;

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
    @required this.type,
    @required this.imagePreview,
    @required this.imageSource,
    @required this.imageBlurred,
    @required this.text,
    @required this.gif,
    @required this.video,
  });

  Post.fromJson(Map<String, dynamic> submission)
      : this(
          id: submission.get('name'),
          subreddit: Subreddit.fromJson(submission),
          author: Author.fromJson(submission),
          created: DateTime.fromMillisecondsSinceEpoch(
            submission.get<double>('created_utc').toInt() * 1000,
            isUtc: true,
          ),
          title: submission.get('title'),
          nsfw: submission.get('nsfw') ?? false,
          spoiler: submission.get('spoiler') ?? false,
          comments: submission.get('num_comments'),
          score: submission.get('score'),
          domain: submission.domain,
          selfDomain: submission.isSelfDomain,
          postUrl: submission.get('permalink'),
          contentUrl: submission.url,
          type: submission.contentType,
          imagePreview: submission.image('preview.images[0].resolutions[0]'),
          imageSource: submission.image('preview.images[0].source'),
          imageBlurred: submission.image(
            'preview.images[0].variants.nsfw.resolutions[0]',
          ),
          text: submission.text,
          gif: submission.gif,
          video: submission.video,
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
