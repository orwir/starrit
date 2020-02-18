import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/post.dart';
import 'package:http/http.dart' as http;

class FeedScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => FeedScreenState();
}

class FeedScreenState extends State<FeedScreen> {
  Feed feed = Feed.home();
  List<Post> posts = <Post>[];

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: Text(
            '${feed.subreddit.isEmpty ? "/home" : feed.subreddit}${feed.sort}',
          ),
        ),
        body: ListView.builder(
          itemBuilder: (BuildContext context, int position) {
            if (position == posts.length) {
              final after = posts.isEmpty ? null : posts[posts.length - 1].id;
              fetchPosts(feed, after: after).then((data) {
                setState(() {
                  posts.addAll(data);
                });
              });
              return LinearProgressIndicator();
            } else if (position > posts.length) {
              return null;
            } else {
              final post = posts[position];
              return Card(
                child: Container(
                  padding: EdgeInsets.all(16.0),
                  child: Text(post.title),
                ),
              );
            }
          },
        ),
      );
}

Future<List<Post>> fetchPosts(Feed feed, {String after}) async {
  await Future.delayed(Duration(seconds: 2));
  final url =
      'https://reddit.com${feed.subreddit}${feed.sort}.json?after=$after';
  final response = await http.get(url);
  if (response.statusCode == 200) {
    final result = jsonDecode(response.body);
    final posts = (result['data']['children'] as List<dynamic>)
        .map((json) => Post.fromJson(json['data']))
        .toList();
    return posts;
  } else {
    throw 'Http error: ${response.statusCode}';
  }
}
