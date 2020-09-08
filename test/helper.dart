import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:mockito/mockito.dart';
import 'package:redux/redux.dart';
import 'package:starrit/app/state.dart';
import 'package:starrit/feed/model/author.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:starrit/feed/model/subreddit.dart';
import 'package:starrit/reducer.dart';

Store<AppState> testStore({
  Iterable<Middleware<AppState>> middlwares = const [],
}) =>
    Store<AppState>(
      starritReducer,
      initialState: AppState.initial,
      middleware: middlwares,
    );

Widget testWrapper(Store<AppState> store, Widget target) => MaterialApp(
      home: StoreProvider<AppState>(
        store: store,
        child: target,
      ),
    );

// ignore: must_be_immutable
class MockPost extends Mock implements Post {
  MockPost() {
    when(id).thenReturn('1');
    when(subreddit).thenReturn(Subreddit('subreddit'));
    when(author).thenReturn(Author('1', 'author'));
    when(created).thenReturn(DateTime.now());
    when(title).thenReturn('title #$id');
    when(nsfw).thenReturn(false);
    when(spoiler).thenReturn(false);
    when(comments).thenReturn(0);
    when(score).thenReturn(0);
    when(hideScore).thenReturn(false);
    when(domain).thenReturn('self.reddit');
    when(selfDomain).thenReturn(true);
    when(postUrl).thenReturn('https://reddit.com/r/sub/example');
    when(contentUrl).thenReturn('https://reddit.com/r/sub/example');
    when(type).thenReturn(PostType.link);
  }
}
