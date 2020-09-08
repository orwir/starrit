import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/common/util/object.dart';
import 'package:starrit/feed/model/author.dart';
import 'package:starrit/feed/model/image.dart';
import 'package:starrit/feed/model/subreddit.dart';

/// Type of a post content.
enum PostType { image, gif, video, text, link }

/// Post information.
@immutable
class Post {
  /// ID (fullname).
  final String id;

  /// Parent subreddit.
  final Subreddit subreddit;

  /// Author.
  final Author author;

  /// Post creation date/time.
  final DateTime created;

  /// Title.
  final String title;

  /// Whether post content marked as adults-only.
  final bool nsfw;

  /// Whether post marked as spoiler.
  final bool spoiler;

  /// Comments count.
  final int comments;

  /// Score.
  final int score;

  /// Whether score should be hidden.
  final bool hideScore;

  /// Source domain of a content.
  final String domain;

  /// Whether content originally posted on Reddit.com.
  final bool selfDomain;

  /// Url to this post on Reddit.com
  final String postUrl;

  /// Url to original content.
  final String contentUrl;

  /// Type of post's content.
  final PostType type;

  /// Post images.
  final PostImage image;

  /// If post is textual contains data.
  final String text;

  /// If post is a gif contains url.
  final String gif;

  /// If post is a video contains url.
  final String video;

  /// For debug purposes only contains original json data.
  final String raw;

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
    @required this.hideScore,
    @required this.domain,
    @required this.selfDomain,
    @required this.postUrl,
    @required this.contentUrl,
    @required this.type,
    @required this.image,
    @required this.text,
    @required this.gif,
    @required this.video,
    this.raw,
  })  : assert(id != null),
        assert(subreddit != null),
        assert(author != null),
        assert(created != null),
        assert(title != null),
        assert(nsfw != null),
        assert(spoiler != null),
        assert(comments != null),
        assert(score != null),
        assert(hideScore != null),
        assert(domain != null),
        assert(selfDomain != null),
        assert(postUrl != null),
        assert(contentUrl != null),
        assert(type != null);

  factory Post.fromJson(Map<String, dynamic> json) {
    final String domain = json['domain'];
    final bool selfDomain = domain.startsWith('self.');
    final String url = json['url'];
    final text = json.string('selftext_html') ?? json.string('selftext');
    final gif = url.takeIf((url) => url.endsWith('.gif'));
    final video = _extractVideo(json, domain, url);
    final image = url.takeIf((url) {
      final Uri uri = Uri.parse(url);
      return _imageExtensions.any(uri.path.endsWith);
    });
    var type = PostType.link;
    if (video != null) {
      type = PostType.video;
    } else if (gif != null) {
      type = PostType.gif;
    } else if (image != null) {
      type = PostType.image;
    } else if (text != null || selfDomain) {
      type = PostType.text;
    }

    return Post(
      id: json['name'] ?? '',
      subreddit: Subreddit.fromJson(json),
      author: Author.fromJson(json),
      created: json.get<num>('created_utc')?.into(
                (seconds) => DateTime.fromMillisecondsSinceEpoch(
                  seconds.toInt() * 1000,
                  isUtc: true,
                ),
              ) ??
          0,
      title: json['title'] ?? '',
      nsfw: json['over_18'] ?? false,
      spoiler: json['spoiler'] ?? false,
      comments: json['num_comments']?.toInt() ?? 0,
      score: json['score']?.toInt() ?? 0,
      hideScore: json['hide_score'] ?? false,
      domain: domain,
      selfDomain: selfDomain,
      postUrl: json['permalink'],
      contentUrl: url,
      type: type,
      image: _extractPostImage(json, image),
      text: text,
      gif: gif,
      video: video,
      raw: kReleaseMode ? null : jsonEncode(json),
    );
  }

  @override
  int get hashCode => hash([
        id,
        subreddit,
        author,
        created,
        title,
        nsfw,
        spoiler,
        comments,
        score,
        hideScore,
        domain,
        postUrl,
        contentUrl,
        type,
        image,
        text,
        gif,
        video,
      ]);

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
            score == other.score &&
            hideScore == other.hideScore &&
            domain == other.domain &&
            postUrl == other.postUrl &&
            contentUrl == other.contentUrl &&
            type == other.type &&
            image == other.image &&
            text == other.text &&
            gif == other.gif &&
            video == other.video;
  }

  @override
  String toString() => '{ '
      'id:$id'
      ', subreddit:$subreddit'
      ', author:$author'
      ', created:$created'
      ', title:$title'
      ', nsfw:$nsfw'
      ', spoiler:$spoiler'
      ', comments:$comments'
      ', score:$score'
      ', hideScore:$hideScore'
      ', domain:$domain'
      ', postUrl:$postUrl'
      ', contentUrl:$contentUrl'
      ', type:$type'
      ', image:$image'
      ', text:$text'
      ', gif:$gif'
      ', video:$video'
      ' }';
}

String _extractVideo(Map<String, dynamic> json, String domain, String url) {
  if (domain == 'i.imgur.com' && url.endsWith('.gifv')) {
    return url.replaceAll('http://', 'https://').replaceAll('.gifv', '.mp4');
  }
  if (domain == 'v.redd.it') {
    final key = 'secure_media.reddit_video.hls_url';
    return json.string(key) ?? json.string('crosspost_parent_list[0].$key');
  }
  if (domain == 'gfycat.com') {
    final key = 'secure_media.oembed.thumbnail_url';
    return (json.string(key) ?? json.string('crosspost_parent_list[0].$key'))
        ?.replaceAll('thumbs.gfycat.com', 'giant.gfycat.com')
        ?.replaceAll('-size_restricted', 'replace')
        ?.replaceAll('.gif', '.mp4');
  }
  return null;
}

PostImage _extractPostImage(Map<String, dynamic> json, String def) {
  final Map<String, dynamic> source = json.get('preview.images[0].source');
  if (source == null && def == null) {
    return null;
  }
  final Map<String, dynamic> preview =
      json.get('preview.images[0].resolutions[0]');
  final Map<String, dynamic> blurred =
      json.get('preview.images[0].variants.nsfw.resolutions[0]');
  return PostImage(
    source: source?.string('url') ?? def,
    width: source?.get<int>('width'),
    height: source?.get<int>('height'),
    preview: preview?.string('url'),
    blurred: blurred?.string('url'),
  );
}

const _imageExtensions = [
  'bmp',
  'jpg',
  'jpeg',
  'png',
  'svg',
  'tif',
  'tiff',
  'jfif',
  'pjpeg',
  'pjp',
  'ico',
  'cur',
];
