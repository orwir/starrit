import 'package:flutter/foundation.dart';

import 'subreddit.dart';
import 'image.dart';

@immutable
class Post {
  final String id;
  final Subreddit subreddit;
  final String author;
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
          id: json['id'] as String,
          subreddit: null, //todo: implement it
          author: json['author'] as String,
          created: json['created_utc'] as int,
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

  Post.empty()
      : this(
          id: null,
          subreddit: null,
          author: null,
          created: null,
          title: null,
          nsfw: null,
          spoiler: null,
          comments: null,
          score: null,
          domain: null,
          postUrl: null,
          contentUrl: null,
          imagePreview: null,
          imageSource: null,
          imageBlurred: null,
        );
}
