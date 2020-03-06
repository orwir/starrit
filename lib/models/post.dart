import 'package:flutter/foundation.dart';
import 'package:starrit/utils/json.dart';
import 'package:starrit/utils/object.dart';
import 'subreddit.dart';
import 'author.dart';
import 'image.dart';

enum PostType { image, gif, video, text, link }

@immutable
class Post {
  static Post fromJson(Map<String, dynamic> json) => _parse(json);

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
    @required this.image,
    @required this.text,
    @required this.gif,
    @required this.video,
  });

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
  final PostType type;
  final PostImage image;
  final String text;
  final String gif;
  final String video;

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
  String toString() => '{'
      'id:$id'
      ', subreddit:$subreddit'
      ', author:$author'
      ', created:$created'
      ', title:$title'
      ', nsfw:$nsfw'
      ', spoiler:$spoiler'
      ', comments:$comments'
      ', domain:$domain'
      ', postUrl:$postUrl'
      ', contentUrl:$contentUrl'
      ', type:$type'
      ', image:$image'
      ', text:$text'
      ', gif:$gif'
      ', video:$video'
      '}';
}

Post _parse(Map<String, dynamic> json) {
  final String domain = json['domain'];
  final bool selfDomain = domain.startsWith('self.');
  final String url = json['url'];
  final text = json.string('selftext') ?? json.string('selftext_html');
  final gif = url.takeIf((String url) => url.endsWith('.gif'));
  final video = extractVideo(json, domain, url);
  final image = url.takeIf(
    (String url) {
      final Uri uri = Uri.parse(url);
      return _imageExtensions.any(uri.path.endsWith);
    },
  );

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
    created: json.get<double>('created_utc')?.into(
              (double seconds) => DateTime.fromMillisecondsSinceEpoch(
                seconds.toInt() * 1000,
                isUtc: true,
              ),
            ) ??
        0,
    title: json['title'] ?? '',
    nsfw: json['over_18'] ?? false,
    spoiler: json['spoiler'] ?? false,
    comments: json['num_comments'] ?? 0,
    score: json['score'],
    domain: domain,
    selfDomain: selfDomain,
    postUrl: json['permalink'],
    contentUrl: url,
    type: type,
    image: extractPostImage(json, image),
    text: text,
    gif: gif,
    video: video,
  );
}

String extractVideo(Map<String, dynamic> json, String domain, String url) {
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

PostImage extractPostImage(Map<String, dynamic> json, String def) {
  final Map<String, dynamic> source = json.get('preview.images[0].source');
  final Map<String, dynamic> preview =
      json.get('preview.images[0].resolutions[0]');
  final Map<String, dynamic> blurred =
      json.get('preview.images[0].variants.nsfw.resolutions[0]');
  return PostImage(
    source: source?.string('url') ?? def,
    width: source?.get<int>('width')?.toDouble(),
    height: source?.get<int>('height')?.toDouble(),
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
