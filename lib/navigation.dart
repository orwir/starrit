import 'package:flutter/material.dart';

import 'feed/models/feed.dart';
import 'feed/screen.dart';
import 'search/screen.dart';

final routes = <String, WidgetBuilder>{
  FeedScreen.routeName: (BuildContext context) {
    final Feed feed =
        ModalRoute.of(context).settings.arguments ?? Type.home + Sort.best;
    return FeedScreen(feed);
  },
  SearchScreen.routeName: (BuildContext context) => SearchScreen(),
};
