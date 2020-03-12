import 'dart:convert';
import 'dart:io';

import 'package:redux_epics/redux_epics.dart';
import 'package:rxdart/rxdart.dart';
import 'package:starrit/feed/services.dart';
import 'package:starrit/common/models/state.dart';
import 'package:starrit/common/utils/json.dart';

import 'actions.dart';
import 'models/feed.dart';
import 'models/post.dart';

final epic = combineEpics<AppState>([
  _feedRequestEpic,
]);

Stream<dynamic> _feedRequestEpic(
  Stream<dynamic> actions,
  EpicStore<AppState> store,
) {
  dynamic _fetch(Feed feed, String after) async {
    try {
      final response = await listing(
        domain: 'reddit.com',
        feed: feed,
        after: after,
      );
      if (response.statusCode != 200) {
        return FeedResponseFailureAction(
          feed,
          HttpException('Usuccessful request. Code: ${response.statusCode}'),
        );
      }
      final Map<String, dynamic> result = jsonDecode(response.body);
      final posts = result
          .get<List>('data.children')
          .map((json) => Post.fromJson(json['data']))
          .toList();
      final next = result.string('data.after');
      return FeedResponseSuccessAction(feed, posts, next);
    } on Exception catch (e) {
      return FeedResponseFailureAction(feed, e);
    }
  }

  return actions
      .distinct()
      .whereType<FeedRequestAction>()
      .switchMap((request) => Stream.fromFuture(
            _fetch(request.feed, request.after),
          ).takeUntil(
            actions
                .whereType<FeedDisposeAction>()
                .where((disposed) => disposed.feed == request.feed),
          ));
}
