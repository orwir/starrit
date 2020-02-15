import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:starrit/model/feed.dart';
import 'package:starrit/model/state.dart';
import 'package:starrit/screen/feed.dart';
import 'package:starrit/screen/saved.dart';

class PostsScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => PostsScreenState();
}

class PostsScreenState extends State<PostsScreen> {
  var _index = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _currentScreen(_index),
      bottomNavigationBar: BottomNavigationBar(
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.library_books),
            title: Text('Feed'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.collections_bookmark),
            title: Text('Saved'),
          )
        ],
        onTap: (index) {
          setState(() {
            _index = index;
          });
        },
        currentIndex: _index,
      ),
    );
  }

  Widget _currentScreen(int index) {
    switch (index) {
      case 0:
        return StoreConnector<AppState, Feed>(
          distinct: true,
          builder: (context, feed) => FeedScreen(feed),
          converter: (store) => store.state.feed,
        );
      case 1:
        return SavedScreen();
    }
    throw 'Unknown Screen Index #$index';
  }
}
