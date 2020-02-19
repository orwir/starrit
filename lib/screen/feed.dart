import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';
import 'package:starrit/service/feed.dart';

class FeedScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => FeedScreenState();
}

class FeedScreenState extends State<FeedScreen> {
  Feed feed = Feed.home();
  List<Post> posts = <Post>[];
  bool loading = false;
  Exception error;

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: Text(feed.asTitle),
        ),
        body: ListView.builder(
          itemBuilder: (BuildContext context, int position) {
            if (position == posts.length) {
              _requestPosts();
              return LinearProgressIndicator();
            } else if (position > posts.length) {
              return null;
            } else {
              return _buildPost(context, posts[position]);
            }
          },
        ),
      );

  Widget _buildPost(BuildContext context, Post post) {
    ThemeData theme = Theme.of(context);
    return Card(
      child: Container(
          padding: EdgeInsets.all(16),
          child: Text(
            post.title,
            style: theme.textTheme.title,
          )),
    );
  }

  void _requestPosts() async {
    if (loading) {
      return;
    }
    loading = true;
    final after = posts.isEmpty ? null : posts[posts.length - 1].id;
    final response = await listing(
      client: Client(),
      domain: 'reddit.com',
      feed: feed,
      after: after,
    );
    if (response.statusCode == 200) {
      final result = jsonDecode(response.body);
      final posts = (result['data']['children'] as List<dynamic>)
          .map((json) => Post.fromJson(json['data']))
          .toList();
      setState(() {
        this.posts.addAll(posts);
        loading = false;
      });
    } else {
      setState(() {
        error =
            HttpException('Request failed with code: ${response.statusCode}');
        loading = false;
      });
    }
  }
}
