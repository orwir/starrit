import 'package:starrit/model/image.dart';
import 'package:starrit/util/json.dart';

enum ContentType { link, text, image, gif, video }

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

extension PostContentJson on Map<String, dynamic> {
  bool get isSelfDomain => domain.startsWith('self.');

  String get domain => get<String>('domain');

  String get url => get<String>('url');

  ContentType get contentType {
    if (_hasText) return ContentType.text;
    if (_hasVideo) return ContentType.video;
    if (_hasGif) return ContentType.gif;
    if (_hasImage) return ContentType.image;
    return isSelfDomain ? ContentType.text : ContentType.link;
  }

  String get text {
    return nonEmpty('selftext_html') ?? nonEmpty('selftext');
  }

  ImageData image(String path) {
    final jsonImage = this.path(path);
    return jsonImage != null ? ImageData.fromJson(jsonImage) : null;
  }

  String get gif => _hasGif ? url : null;

  String get video {
    if (domain == 'i.imgur.com' && url.endsWith('.gifv')) {
      return url.replaceAll('http://', 'https://').replaceAll('.gifv', '.mp4');
    }
    if (domain == 'v.redd.it') {
      const path = 'secure_media.reddit_video.hls_url';
      return this.path<String>(path) ??
          this.path<String>('crosspost_parent_list[0].$path');
    }
    if (domain == 'gfycat.com') {
      const path = 'secure_media.oembed.thumbnail_url';
      final url = this.path<String>(path) ??
          this.path<String>('crosspost_parent_list[0].$path');
      if (url != null) {
        return url
            .replaceAll('thumbs.gfycat.com', 'giant.gfycat.com')
            .replaceAll('-size_restricted', 'replace')
            .replaceAll('.gif', '.mp4');
      }
    }
    return null;
  }

  bool get _hasText => text != null;

  bool get _hasVideo => video != null;

  bool get _hasGif => url.endsWith('.gif');

  bool get _hasImage {
    final lastSegment = Uri.parse(get('url')).pathSegments.last;
    final ext = lastSegment?.substring(lastSegment.lastIndexOf('.') + 1);
    return _imageExtensions.contains(ext?.toLowerCase());
  }
}
