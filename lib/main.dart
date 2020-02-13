import 'package:flutter/material.dart';
import 'package:starrit/feed.dart';
import 'package:starrit/saved.dart';

main() {
  runApp(MaterialApp(
    title: 'Stare It!',
    home: DataScreen(),
  ));
}

class DataScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => DataScreenState();
}

class DataScreenState extends State<DataScreen> {
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
        {
          return FeedScreen();
        }
        break;
      case 1:
        {
          return SavedScreen();
        }
    }
    throw 'Unknown Screen Index #$index';
  }
}
