import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:starrit/screen/feed.dart';

main() {
  runApp(StarritApp());
}

class StarritApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.light(),
      darkTheme: ThemeData.dark(),
      home: FeedScreen(),
    );
  }
}
