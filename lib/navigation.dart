import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:starrit/models/feed.dart';
import 'package:starrit/screens/feed/feed.dart';
import 'package:starrit/screens/search/search.dart';

final routes = <String, WidgetBuilder>{
  FeedScreen.routeName: (BuildContext context) {
    final Feed feed =
        ModalRoute.of(context).settings.arguments ?? Type.home + Sort.best;
    return FeedScreen(feed);
  },
  SearchScreen.routeName: (BuildContext context) => SearchScreen(),
};
