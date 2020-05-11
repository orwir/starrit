import 'package:flutter/material.dart';
import 'package:starrit/common/models/feed.dart';
import 'package:starrit/feed/screen.dart';
import 'package:starrit/search/screen.dart';

final routes = <String, WidgetBuilder>{
  FeedScreen.routeName: (BuildContext context) {
    final Feed feed =
        ModalRoute.of(context).settings.arguments ?? Type.home + Sort.best;
    return FeedScreen(feed);
  },
  SearchScreen.routeName: (BuildContext context) => SearchScreen(),
};
