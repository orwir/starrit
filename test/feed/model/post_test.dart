import 'dart:convert';

import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/feed/model/post.dart';

void main() {
  group('Post.fromJson', () {
    test(
      'should build an object when valid json passed.',
      () {
        final post = Post.fromJson(jsonDecode(_realPostData));

        expect(post, isNotNull);
        expect(post.id, 't3_iafzyj');
        expect(post.subreddit.name, 'pathofexile');
        expect(
          post.subreddit.icon,
          'https://styles.redditmedia.com/t5_2sf6m/styles/communityIcon_ck9weg21jw551.png?width=256&s=419e48e5a0227e2ac05c0437591fe5ee5db1b66c',
        );
        expect(
          post.subreddit.banner,
          'https://b.thumbs.redditmedia.com/eyLZ5wnxKjaIRgOxhC1LUi5T94ATTb0Zx7pP-recepU.png',
        );
        expect(post.author.id, 't2_c94qi');
        expect(post.author.name, 'Castellorizon');
        expect(post.created, DateTime.utc(2020, 8, 15, 21, 38, 1));
        expect(
          post.title,
          'Please stop misrepresenting the issue: we\'re NOT asking for an easier game, we\'re asking for a clear path of grindy and steady progression.',
        );
        expect(post.nsfw, false);
        expect(post.spoiler, false);
        expect(post.comments, 1058);
        expect(post.score, 1687);
        expect(post.hideScore, false);
        expect(post.domain, 'self.pathofexile');
        expect(post.selfDomain, true);
        expect(
          post.postUrl,
          '/r/pathofexile/comments/iafzyj/please_stop_misrepresenting_the_issue_were_not/',
        );
        expect(
          post.contentUrl,
          'https://www.reddit.com/r/pathofexile/comments/iafzyj/please_stop_misrepresenting_the_issue_were_not/',
        );
        expect(post.type, PostType.text);
        expect(post.image, isNull);
        expect(post.text, isNotNull);
        expect(post.gif, isNull);
        expect(post.video, isNull);
      },
    );

    test('subreddit icon should set community_icon when it is not null', () {
      final json = jsonDecode(_realPostData);
      json['sr_detail']['community_icon'] = 'community_icon';

      final post = Post.fromJson(json);

      expect(post.subreddit.icon, 'community_icon');
    });

    test(
      'subreddit icon should set icon_img when community_icon is null.',
      () {
        final json = jsonDecode(_realPostData);
        json['sr_detail']['community_icon'] = null;
        json['sr_detail']['icon_img'] = 'icon_img';

        final post = Post.fromJson(json);

        expect(post.subreddit.icon, 'icon_img');
      },
    );

    test('subreddit banner should set banner_img when it is not null', () {
      final json = jsonDecode(_realPostData);
      json['sr_detail']['banner_img'] = 'banner_img';

      final post = Post.fromJson(json);

      expect(post.subreddit.banner, 'banner_img');
    });

    test(
      'subreddit banner should set header_img when banner_img is null',
      () {
        final json = jsonDecode(_realPostData);
        json['sr_detail']['banner_img'] = null;
        json['sr_detail']['header_img'] = 'header_img';

        final post = Post.fromJson(json);

        expect(post.subreddit.banner, 'header_img');
      },
    );

    test('author is not deleted when author.id is not null', () {
      final json = jsonDecode(_realPostData);
      json['author_fullname'] = '123';

      final post = Post.fromJson(json);

      expect(post.author.deleted, false);
    });

    test('author is deleted when author.id is null', () {
      final json = jsonDecode(_realPostData);
      json['author_fullname'] = null;

      final post = Post.fromJson(json);

      expect(post.author.deleted, true);
    });

    test('selfDomain should be true when domain starts with "self."', () {
      final json = jsonDecode(_realPostData);
      json['domain'] = 'self.randomsubreddit';

      final post = Post.fromJson(json);

      expect(post.selfDomain, true);
    });

    test(
      'selfDomain should be false when domain does not start with "self."',
      () {
        final json = jsonDecode(_realPostData);
        json['domain'] = 'random-domain.com';

        final post = Post.fromJson(json);

        expect(post.selfDomain, false);
      },
    );

    test(
      'post should has type "text" when all content are null and selfDomain is true.',
      () {
        final json = jsonDecode(_realPostData);
        json['selftext'] = null;
        json['selftext_html'] = null;
        json['url'] = 'https://url.com';
        json['domain'] = 'self.domain';

        final post = Post.fromJson(json);

        expect(post.type, PostType.text);
      },
    );

    test(
      'post should has type "link" when all content are null and selfDomain is false.',
      () {
        final json = jsonDecode(_realPostData);
        json['selftext'] = null;
        json['selftext_html'] = null;
        json['url'] = 'https://url.com';
        json['domain'] = 'domain.com';

        final post = Post.fromJson(json);

        expect(post.type, PostType.link);
      },
    );

    test(
      'post should has type "text" when selftext or selftext_html are not null.',
      () {
        final json = jsonDecode(_realPostData);

        json['selftext'] = 'text';
        json['selftext_html'] = null;
        final post1 = Post.fromJson(json);

        expect(post1.type, PostType.text);
        expect(post1.text, 'text');

        json['selftext'] = null;
        json['selftext_html'] = 'html';
        final post2 = Post.fromJson(json);

        expect(post2.type, PostType.text);
        expect(post2.text, 'html');
      },
    );

    test(
      'post should has type "image" when url ends with supported image extension.',
      () {
        final json = jsonDecode(_realPostData);
        final variants = [
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

        for (final variant in variants) {
          json['url'] = 'https://example.com/image.$variant';
          final post = Post.fromJson(json);
          expect(
            post.type,
            PostType.image,
            reason: 'Image type "$variant" does not supported.',
          );
          expect(
            post.image,
            isNotNull,
            reason: 'Image type "$variant" does not supported.',
          );
        }
      },
    );

    test(
        'should show image without preview when url contains image and preview is null',
        () {
      final json = jsonDecode(_realPostData);
      json['url'] = 'https://example.com/image.jpg';

      final post = Post.fromJson(json);

      expect(post.type, PostType.image);
      expect(post.image.source, isNotNull);
      expect(post.image.hasSize, false);
      expect(post.image.preview, isNull);
      expect(post.image.blurred, isNull);
    });

    test('should extract images data from preview node', () {
      final json = jsonDecode(
        _realPostData.replaceAll('"media": null,', _realImagePreviewNode),
      );

      final post = Post.fromJson(json);

      expect(post.image, isNotNull);
      expect(post.image.preview, isNotNull);
      expect(post.image.source, isNotNull);
      expect(post.image.blurred, isNotNull);
      expect(post.image.width, 1717);
      expect(post.image.height, 2547);
    });

    // TODO: write tests for gif posts
    // TODO: write tests for video posts
  });
}

final _realPostData = r"""
{
  "approved_at_utc": null,
  "subreddit": "pathofexile",
  "selftext": "Let me put you an example: do you gamble fusings to 6 link your armour?\n\nI do not. Ever. I absolutely hate losing my progress to end up in a worse position than I was before. So the game offers an alternate, clear path to upgrade my gear:\n\n1) Map to farm tons of sulphite\n\n2) Delve to 250+ depth to find the 6 link recipe\n\n3) Farm 1500 fusings or equivalent currency.\n\nAnd this awesome, because I have to farm and grind and actually put effort into accomplishing a clear, attainable goal that I check off my list and continue working towards the next objective. This is proper ARPG progression and feels rewarding.\n\nYou want to take a shortcut and try your luck? You absolutely can! Maybe you 6 link like a streamer in a couple of fusings and make me look like an idiot. And you know what? Good for you! As far as I'm concerned, the more power to you, the better.\n\nThe game is designed well enough to accomodate both our preferences in regards to socket and linking management.\n\nHarvest has opened crafting for the first time to many, many players because it provides the equivalent of the 6 link recipe: you do this, and you get that. \nIs it too easy to craft uber powerful ítems? Maybe, yes.\nAre 6xT1 items really needed? No. \nIs deterministic crafting needed in at least some accesible form? Absolutely.\n\nBecause when Harvest goes away, crafting goes away too and it's back to trading. There's no in between. And that will be a sad day for people like me who really loves the game.",
  "author_fullname": "t2_c94qi",
  "saved": false,
  "mod_reason_title": null,
  "gilded": 1,
  "clicked": false,
  "title": "Please stop misrepresenting the issue: we're NOT asking for an easier game, we're asking for a clear path of grindy and steady progression.",
  "link_flair_richtext": [
    {
      "e": "text",
      "t": "Fluff"
    }
  ],
  "subreddit_name_prefixed": "r/pathofexile",
  "hidden": false,
  "pwls": 6,
  "link_flair_css_class": null,
  "downs": 0,
  "thumbnail_height": null,
  "top_awarded_type": null,
  "hide_score": false,
  "name": "t3_iafzyj",
  "quarantine": false,
  "link_flair_text_color": "light",
  "upvote_ratio": 0.82,
  "author_flair_background_color": null,
  "subreddit_type": "public",
  "ups": 1687,
  "total_awards_received": 4,
  "media_embed": {},
  "thumbnail_width": null,
  "author_flair_template_id": null,
  "is_original_content": false,
  "user_reports": [],
  "secure_media": null,
  "is_reddit_media_domain": false,
  "is_meta": false,
  "category": null,
  "secure_media_embed": {},
  "link_flair_text": "Fluff",
  "can_mod_post": false,
  "score": 1687,
  "approved_by": null,
  "author_premium": true,
  "thumbnail": "self",
  "edited": false,
  "author_flair_css_class": null,
  "author_flair_richtext": [],
  "gildings": {
    "gid_2": 1
  },
  "content_categories": null,
  "is_self": true,
  "mod_note": null,
  "created": 1597556281,
  "link_flair_type": "richtext",
  "wls": 6,
  "removed_by_category": null,
  "banned_by": null,
  "author_flair_type": "text",
  "domain": "self.pathofexile",
  "allow_live_comments": true,
  "selftext_html": "<!-- SC_OFF --><div class=\"md\"><p>Let me put you an example: do you gamble fusings to 6 link your armour?</p>\n\n<p>I do not. Ever. I absolutely hate losing my progress to end up in a worse position than I was before. So the game offers an alternate, clear path to upgrade my gear:</p>\n\n<p>1) Map to farm tons of sulphite</p>\n\n<p>2) Delve to 250+ depth to find the 6 link recipe</p>\n\n<p>3) Farm 1500 fusings or equivalent currency.</p>\n\n<p>And this awesome, because I have to farm and grind and actually put effort into accomplishing a clear, attainable goal that I check off my list and continue working towards the next objective. This is proper ARPG progression and feels rewarding.</p>\n\n<p>You want to take a shortcut and try your luck? You absolutely can! Maybe you 6 link like a streamer in a couple of fusings and make me look like an idiot. And you know what? Good for you! As far as I&#39;m concerned, the more power to you, the better.</p>\n\n<p>The game is designed well enough to accomodate both our preferences in regards to socket and linking management.</p>\n\n<p>Harvest has opened crafting for the first time to many, many players because it provides the equivalent of the 6 link recipe: you do this, and you get that. \nIs it too easy to craft uber powerful ítems? Maybe, yes.\nAre 6xT1 items really needed? No. \nIs deterministic crafting needed in at least some accesible form? Absolutely.</p>\n\n<p>Because when Harvest goes away, crafting goes away too and it&#39;s back to trading. There&#39;s no in between. And that will be a sad day for people like me who really loves the game.</p>\n</div><!-- SC_ON -->",
  "likes": null,
  "suggested_sort": null,
  "banned_at_utc": null,
  "view_count": null,
  "archived": false,
  "no_follow": false,
  "is_crosspostable": true,
  "pinned": false,
  "over_18": false,
  "all_awardings": [
    {
      "giver_coin_reward": null,
      "subreddit_id": null,
      "is_new": false,
      "days_of_drip_extension": 0,
      "coin_price": 30,
      "id": "award_b4ff447e-05a5-42dc-9002-63568807cfe6",
      "penny_donate": null,
      "award_sub_type": "PREMIUM",
      "coin_reward": 0,
      "icon_url": "https://i.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png",
      "days_of_premium": 0,
      "resized_icons": [
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=16&height=16&auto=webp&s=49b775b684dcffe79df3e103d71055a7925d6c37",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=32&height=32&auto=webp&s=31e8c0e96f4a97ee1bf582ab8f9a21e06fc85e01",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=48&height=48&auto=webp&s=0a6fb9ecfb8eee4493afe6c5b234c44eb8413008",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=64&height=64&auto=webp&s=51ea8c05c28899739458535e90d97210889aea91",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=128&height=128&auto=webp&s=093c7a95723b58ea1373bf62223e2ae7f11323fb",
          "width": 128,
          "height": 128
        }
      ],
      "icon_width": 2048,
      "static_icon_width": 2048,
      "start_date": null,
      "is_enabled": true,
      "description": "A glowing commendation for all to see",
      "end_date": null,
      "subreddit_coin_reward": 0,
      "count": 1,
      "static_icon_height": 2048,
      "name": "All-Seeing Upvote",
      "resized_static_icons": [
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=16&height=16&auto=webp&s=49b775b684dcffe79df3e103d71055a7925d6c37",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=32&height=32&auto=webp&s=31e8c0e96f4a97ee1bf582ab8f9a21e06fc85e01",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=48&height=48&auto=webp&s=0a6fb9ecfb8eee4493afe6c5b234c44eb8413008",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=64&height=64&auto=webp&s=51ea8c05c28899739458535e90d97210889aea91",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png?width=128&height=128&auto=webp&s=093c7a95723b58ea1373bf62223e2ae7f11323fb",
          "width": 128,
          "height": 128
        }
      ],
      "icon_format": null,
      "icon_height": 2048,
      "penny_price": null,
      "award_type": "global",
      "static_icon_url": "https://i.redd.it/award_images/t5_22cerq/rg960rc47jj41_All-SeeingUpvote.png"
    },
    {
      "giver_coin_reward": null,
      "subreddit_id": null,
      "is_new": false,
      "days_of_drip_extension": 0,
      "coin_price": 500,
      "id": "gid_2",
      "penny_donate": null,
      "award_sub_type": "GLOBAL",
      "coin_reward": 100,
      "icon_url": "https://www.redditstatic.com/gold/awards/icon/gold_512.png",
      "days_of_premium": 7,
      "resized_icons": [
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_16.png",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_32.png",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_48.png",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_64.png",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_128.png",
          "width": 128,
          "height": 128
        }
      ],
      "icon_width": 512,
      "static_icon_width": 512,
      "start_date": null,
      "is_enabled": true,
      "description": "Gives the author a week of Reddit Premium, %{coin_symbol}100 Coins to do with as they please, and shows a Gold Award.",
      "end_date": null,
      "subreddit_coin_reward": 0,
      "count": 1,
      "static_icon_height": 512,
      "name": "Gold",
      "resized_static_icons": [
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_16.png",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_32.png",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_48.png",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_64.png",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://www.redditstatic.com/gold/awards/icon/gold_128.png",
          "width": 128,
          "height": 128
        }
      ],
      "icon_format": null,
      "icon_height": 512,
      "penny_price": null,
      "award_type": "global",
      "static_icon_url": "https://www.redditstatic.com/gold/awards/icon/gold_512.png"
    },
    {
      "giver_coin_reward": 0,
      "subreddit_id": null,
      "is_new": false,
      "days_of_drip_extension": 0,
      "coin_price": 75,
      "id": "award_ce5f9ce6-49d9-4905-9228-22950e889206",
      "penny_donate": 0,
      "award_sub_type": "GLOBAL",
      "coin_reward": 0,
      "icon_url": "https://i.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png",
      "days_of_premium": 0,
      "resized_icons": [
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=16&height=16&auto=webp&s=7a3164ef705ae0a41198e29767e4a51fd86e2dcd",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=32&height=32&auto=webp&s=5c621179e63f7f6fbb6a0807367bd79467878784",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=48&height=48&auto=webp&s=ebf40f79a711e9c4206f5f841235e43697f7a3f5",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=64&height=64&auto=webp&s=18dad26bb669e202c42ff6b742d6c0f9d1e62ee9",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=128&height=128&auto=webp&s=61ccae2b32cfbf2f26bd53c9d1287d1235da4d68",
          "width": 128,
          "height": 128
        }
      ],
      "icon_width": 2048,
      "static_icon_width": 2048,
      "start_date": null,
      "is_enabled": true,
      "description": "Everything's better with a good hug",
      "end_date": null,
      "subreddit_coin_reward": 0,
      "count": 1,
      "static_icon_height": 2048,
      "name": "Hugz",
      "resized_static_icons": [
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=16&height=16&auto=webp&s=7a3164ef705ae0a41198e29767e4a51fd86e2dcd",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=32&height=32&auto=webp&s=5c621179e63f7f6fbb6a0807367bd79467878784",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=48&height=48&auto=webp&s=ebf40f79a711e9c4206f5f841235e43697f7a3f5",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=64&height=64&auto=webp&s=18dad26bb669e202c42ff6b742d6c0f9d1e62ee9",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png?width=128&height=128&auto=webp&s=61ccae2b32cfbf2f26bd53c9d1287d1235da4d68",
          "width": 128,
          "height": 128
        }
      ],
      "icon_format": "PNG",
      "icon_height": 2048,
      "penny_price": 0,
      "award_type": "global",
      "static_icon_url": "https://i.redd.it/award_images/t5_22cerq/5smbysczm1w41_Hugz.png"
    },
    {
      "giver_coin_reward": 0,
      "subreddit_id": null,
      "is_new": false,
      "days_of_drip_extension": 0,
      "coin_price": 70,
      "id": "award_7becef23-fb0b-4d62-b8a6-01d5759367cb",
      "penny_donate": 0,
      "award_sub_type": "GLOBAL",
      "coin_reward": 0,
      "icon_url": "https://i.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png",
      "days_of_premium": 0,
      "resized_icons": [
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=16&height=16&auto=webp&s=19c8ba1570a2447a04354e05a9463f3d2063f522",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=32&height=32&auto=webp&s=6222517b5d76c737ce1ad1ab55c42e3ce53c11d7",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=48&height=48&auto=webp&s=5f5d88a13a1a514298ec5c7edc6f2506750f3c4a",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=64&height=64&auto=webp&s=3af85a35bcd871d432337f309f6ea333181b4092",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=128&height=128&auto=webp&s=4631e5c3e2cda226cb2725e9eff118c7b419a95e",
          "width": 128,
          "height": 128
        }
      ],
      "icon_width": 2048,
      "static_icon_width": 2048,
      "start_date": null,
      "is_enabled": true,
      "description": "When goodness lifts you",
      "end_date": null,
      "subreddit_coin_reward": 0,
      "count": 1,
      "static_icon_height": 2048,
      "name": "Faith In Humanity Restored",
      "resized_static_icons": [
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=16&height=16&auto=webp&s=19c8ba1570a2447a04354e05a9463f3d2063f522",
          "width": 16,
          "height": 16
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=32&height=32&auto=webp&s=6222517b5d76c737ce1ad1ab55c42e3ce53c11d7",
          "width": 32,
          "height": 32
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=48&height=48&auto=webp&s=5f5d88a13a1a514298ec5c7edc6f2506750f3c4a",
          "width": 48,
          "height": 48
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=64&height=64&auto=webp&s=3af85a35bcd871d432337f309f6ea333181b4092",
          "width": 64,
          "height": 64
        },
        {
          "url": "https://preview.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png?width=128&height=128&auto=webp&s=4631e5c3e2cda226cb2725e9eff118c7b419a95e",
          "width": 128,
          "height": 128
        }
      ],
      "icon_format": "PNG",
      "icon_height": 2048,
      "penny_price": 0,
      "award_type": "global",
      "static_icon_url": "https://i.redd.it/award_images/t5_22cerq/gva4vt20qc751_FaithInHumanityRestored.png"
    }
  ],
  "awarders": [],
  "media_only": false,
  "link_flair_template_id": "6bfc74f0-de1f-11e7-b60f-0e9caab1799a",
  "sr_detail": {
    "default_set": true,
    "banner_img": "https://b.thumbs.redditmedia.com/eyLZ5wnxKjaIRgOxhC1LUi5T94ATTb0Zx7pP-recepU.png",
    "restrict_posting": true,
    "user_is_banned": false,
    "free_form_reports": true,
    "community_icon": "https://styles.redditmedia.com/t5_2sf6m/styles/communityIcon_ck9weg21jw551.png?width=256&s=419e48e5a0227e2ac05c0437591fe5ee5db1b66c",
    "show_media": true,
    "description": "###\n#Daily Deals\n[~m~](/s)\n> [Project Body Armour  discounted to 185 Points ](https://www.pathofexile.com/shop/item/ProjectBodyArmour)[ and 19 more...](https://pathofexile.com/shop/category/daily-deals)\n\n[Last updated at 12:33:33 UTC](/smallText)\n\n[~s~](/s)\n\n#####Patch Timeline (PST)\n\n**Tentative Dates**\n\n**3.11 (Harvest) Ends (PC):** [September 14th 3pm](https://www.pathofexile.com/forum/view-thread/2919200)\n\n**3.11 (Harvest) Ends (Console):** [TBA](https://www.pathofexile.com/forum/view-thread/2919200)\n\n**3.12 Launches (PC):** [September 18th](https://www.pathofexile.com/forum/view-thread/2919200)\n\n**3.12 Launches (Console):** [September 23rd](https://www.pathofexile.com/forum/view-thread/2919200)\n\n---\n\n####Stash Tab Sale Dates (PST):\nLast sale: July 17th - July 19th\n\nNext sale: August 7th - August 9th\n\nStash tab sales usually occur every 3 weeks.\n\n---\n\n####[Path of Exile Discord!](https://discord.gg/pathofexile)\n####[Path of Exile Trading Discord](http://discord.gg/VxcYmmN)\n\n---\n\n#####[Light] (http://www.reddit.com/r/pathofexile/) [Dark] (http://dd.reddit.com/r/pathofexile/)\n\n---\n\n####Rules\n* [Subreddit Rules] (http://www.reddit.com/r/pathofexile/wiki/rules)\n\n* [Path of Exile Terms of Use] (https://www.pathofexile.com/legal/terms-of-use-and-privacy-policy)\n\n---\n\n####Community Communication\n\n* **Trial/Completion Sharing:** /global 820 \n\n* **Service/Challenge/Harvest Craft Trading:** /trade 820 or [Path Of Exile Trading Discord](http://discord.gg/VxcYmmN)\n\n* **Sulphite Rotations:** /global 840 or \n\n* **Challenge Completion Trading:** /trade 4040\n\n* **Map Swapping:** /trade 159\n\n* **In-Game Chat:** /global 5055\n\n* **Questions/Help:** /global 411\n\n* **SSF Chat:** /global 773\n\n---\n\n####Useful Links\n\n* [Path of Exile's Main Site](http://pathofexile.com) \n\n* [**GGG's Bugs and To-Do List**](https://www.pathofexile.com/forum/view-thread/1733474)\n\n* [Path of Exile's Twitter](https://twitter.com/pathofexile)\n\n* [Path of Exile's Official Wiki](http://pathofexile.gamepedia.com/Path_of_Exile_Wiki)\n\n* [/r/PathofExile's FAQ Page](/r/pathofexile/wiki/faq)\n\n* [**Beginner's Guide**](https://exiledguides.com/home/beginner-guides/beginner-guide-series/)\n\n* [Build Guide Indexer](http://www.poebuilds.cc/)\n\n* [Harvest Craft Trading Website **(new)**](https://forbiddenharvest.com/)\n\n* [PoE Forums Build Browser](http://timtips.com/buildbrowser/#/list)\n\n* [**Technical Support for Network Issues**](https://www.pathofexile.com/forum/view-thread/1377789/page/1)\n\n* [/r/PathofExile's Daily Labyrinth Discussion](https://www.reddit.com/r/pathofexile/search?q=title%3ADaily+Labyrinth+author%3AAutoModerator&sort=new&restrict_sr=on&t=day)\n\n* [/r/PathofExile's Daily Question Thread](https://www.reddit.com/r/pathofexile/search?q=title%3AQuestions+Thread+author%3AAutoModerator&restrict_sr=on&sort=new&t=day)\n\n* [GGG's Guide to Fixing Connectivity Issues](https://www.pathofexile.com/forum/view-thread/1377789/page/1#p11291009)\n\n* [Unofficial Mac Client](http://www.pathofexile.com/forum/view-thread/48708)\n\n* [Unofficial Linux Client (Newer)](https://www.reddit.com/r/pathofexile/comments/9x47or/guide_getting_path_of_exiledx11_to_run_smoothly/)\n\n* [Unofficial Linux Client (Older)](https://www.reddit.com/r/pathofexile/comments/7fkwwj/guide_to_play_poe_on_linux_with_64bit_and_dx9/?st=jaybuuxz&sh=89ba7cfb)\n\n* [GGG Post Tracker] (http://gggtracker.com)\n\n* [List of PoE Tools](/r/pathofexile/wiki/tools)\n\n* [3.9+ PoE Tools List](https://old.reddit.com/r/pathofexile/comments/fek7kw/compiled_list_of_recently_created_poe_tools_end/)\n\n---\n\n####Related Communities\n\n* [**Path Of Exile Discord**](http://discord.gg/pathofexile)\n\n* [**Path Of Exile Trading Discord**](http://discord.gg/VxcYmmN)\n\n* [PoE Consoles Community](https://www.reddit.com/r/PoE_Consoles/) and [**Path Of Exile Console Discord**] (http://discord.gg/zMaD39P)\n\n* [PoE Hideouts](https://www.reddit.com/r/PathOfExile_Hideouts/) and [#in-game-showcase on Discord!](http://discord.gg/pathofexile)\n\n* [PoE PS4 Community](https://www.reddit.com/r/pathofexileps4/)\n\n* [PoE Mobile Community](https://www.reddit.com/r/pathofexilemobile/)\n\n* [PoE Tool Development](https://www.reddit.com/r/pathofexiledev) and [#tool-dev on Discord!](http://discord.gg/pathofexile)\n\n* [PoE Racing/Speed Running Community](https://www.reddit.com/r/fastpoe)\n\n\n* Looking for Group? \n\n* *  [#looking-for-group on Discord!](http://discord.gg/pathofexile)\n\n* [PoE Builds Subreddit](/r/pathofexilebuilds) \n* *  [#build-planning on Discord](http://discord.gg/pathofexile)\n\n* [Private PoE Leagues](/r/PoEleagues/) \n* *  [#private-leagues-lfm on Discord](http://discord.gg/pathofexile)\n\n---\n\n####Flair Filters\n\n######[Veteran Filter](https://vt.reddit.com/r/pathofexile)\n\n######[No Fluff](https://nf.reddit.com/r/pathofexile)\n\n######[Information](https://io.reddit.com/r/pathofexile)\n\n######[Discussion](https://ds.reddit.com/r/pathofexile)\n\n######[Video](https://vd.reddit.com/r/pathofexile)\n\n######[MTX Showcase](https://mx.reddit.com/r/pathofexile)\n\n######[Item Showcase](https://ie.reddit.com/r/pathofexile)\n\n######[Feedback](https://fk.reddit.com/r/pathofexile)\n\n######[All](https://old.reddit.com/r/pathofexile)",
    "user_is_muted": false,
    "display_name": "pathofexile",
    "header_img": "https://b.thumbs.redditmedia.com/BQkvJlC9JkZvvzdzvC0qTrw-1n3YPFiDZE9wwO4c3dM.png",
    "title": "Path of Exile",
    "previous_names": [],
    "user_is_moderator": false,
    "over_18": false,
    "icon_size": [
      256,
      256
    ],
    "primary_color": "#323232",
    "icon_img": "https://b.thumbs.redditmedia.com/iRPBB0E9jIJ0AxZdEId1vYKafiVJNL8Q-w6TIvJoGZo.png",
    "icon_color": "",
    "submit_link_label": "Submit Link",
    "header_size": [
      31,
      40
    ],
    "restrict_commenting": false,
    "subscribers": 369438,
    "submit_text_label": "Submit Text",
    "link_flair_position": "left",
    "display_name_prefixed": "r/pathofexile",
    "key_color": "",
    "name": "t5_2sf6m",
    "created": 1302272817,
    "url": "/r/pathofexile/",
    "created_utc": 1302244017.0,
    "banner_size": [
      1280,
      384
    ],
    "user_is_contributor": false,
    "public_description": "A subreddit dedicated to Path of Exile, an ARPG made by Grinding Gear Games. Spiritual successor to Diablo 2",
    "link_flair_enabled": true,
    "disable_contributor_requests": false,
    "subreddit_type": "public",
    "user_is_subscriber": true
  },
  "can_gild": true,
  "spoiler": false,
  "locked": false,
  "author_flair_text": null,
  "treatment_tags": [],
  "visited": false,
  "removed_by": null,
  "num_reports": null,
  "distinguished": null,
  "subreddit_id": "t5_2sf6m",
  "mod_reason_by": null,
  "removal_reason": null,
  "link_flair_background_color": "#533a3a",
  "id": "iafzyj",
  "is_robot_indexable": true,
  "report_reasons": null,
  "author": "Castellorizon",
  "discussion_type": null,
  "num_comments": 1058,
  "send_replies": true,
  "whitelist_status": "all_ads",
  "contest_mode": false,
  "mod_reports": [],
  "author_patreon_flair": false,
  "author_flair_text_color": null,
  "permalink": "/r/pathofexile/comments/iafzyj/please_stop_misrepresenting_the_issue_were_not/",
  "parent_whitelist_status": "all_ads",
  "stickied": false,
  "url": "https://www.reddit.com/r/pathofexile/comments/iafzyj/please_stop_misrepresenting_the_issue_were_not/",
  "subreddit_subscribers": 369438,
  "created_utc": 1597527481,
  "num_crossposts": 0,
  "media": null,
  "is_video": false
}
""";

final _realImagePreviewNode = """
"preview": {
  "images": [
    {
      "source": {
        "url": "https://preview.redd.it/ma577ugrv8h51.jpg?auto=webp&s=22c221aadbd1b9b815567d858afe2d852fe373d6",
        "width": 1717,
        "height": 2547
      },
      "resolutions": [
        {
          "url": "https://preview.redd.it/ma577ugrv8h51.jpg?width=108&crop=smart&auto=webp&s=4773545418db261a176a28acd00842d0f9cfda3d",
          "width": 108,
          "height": 160
        },
        {
          "url": "https://preview.redd.it/ma577ugrv8h51.jpg?width=216&crop=smart&auto=webp&s=f0655de38d5fd88bd2d278f4e75b31f83ce77c69",
          "width": 216,
          "height": 320
        },
        {
          "url": "https://preview.redd.it/ma577ugrv8h51.jpg?width=320&crop=smart&auto=webp&s=7124e19f6b5c738ce70a36e6f0cbf43a512961d9",
          "width": 320,
          "height": 474
        },
        {
          "url": "https://preview.redd.it/ma577ugrv8h51.jpg?width=640&crop=smart&auto=webp&s=bdc6aeaff332492ea4355b3373e30355e62686c3",
          "width": 640,
          "height": 949
        },
        {
          "url": "https://preview.redd.it/ma577ugrv8h51.jpg?width=960&crop=smart&auto=webp&s=d1921452f9adbeea6a16aef8db3bc2042629de68",
          "width": 960,
          "height": 1424
        },
        {
          "url": "https://preview.redd.it/ma577ugrv8h51.jpg?width=1080&crop=smart&auto=webp&s=aacc54a66c99687aecff40e25b427c5e90ab491f",
          "width": 1080,
          "height": 1602
        }
      ],
      "variants": {
        "nsfw": {
          "resolutions": [
            {
              "url": "https://preview.redd.it/ma577ugrv8h51.jpg?width=108&crop=smart&auto=webp&s=4773545418db261a176a28acd00842d0f9cfda3d",
              "width": 108,
              "height": 160
            }
          ]
        }
      },
      "id": "N6eS3Uc69WbAeAaFQXrT4NBpSfx8WF1IBcv5mCojwpU"
    }
  ],
  "enabled": true
},
""";
