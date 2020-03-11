import 'dart:convert';
import 'dart:io';

import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/feed/services.dart';
import 'package:starrit/models/state.dart';
import 'package:starrit/utils/json.dart';

import 'actions.dart';
import 'models/post.dart';

final epic = combineEpics<AppState>([
  TypedEpic<AppState, FeedRequestAction>(_feedRequestEpic),
]);

Stream<dynamic> _feedRequestEpic(
  Stream<FeedRequestAction> actions,
  EpicStore<AppState> store,
) =>
    actions.asyncMap((action) async {
      try {
        final response = await listing(
          domain: 'reddit.com',
          feed: action.feed,
          after: action.after,
        );
        if (response.statusCode != 200) {
          return FeedResponseFailureAction(
            action.feed,
            HttpException('Feed data request usuccessful. errorCode=404'),
          );
        }
        final Map<String, dynamic> result = jsonDecode(response.body);
        final posts = result
            .get<List>('data.children')
            .map((json) => Post.fromJson(json['data']))
            .toList();
        final next = result.string('data.after');
        return FeedResponseSuccessAction(action.feed, posts, next);
      } on Exception catch (e) {
        return FeedResponseFailureAction(action.feed, e);
      }
    });
