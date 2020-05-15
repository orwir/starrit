import 'dart:convert';
import 'dart:io';

import 'package:rxdart/rxdart.dart';
import 'package:redux_epics/redux_epics.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/feed/actions.dart';
import 'package:starrit/feed/model/post.dart';
import 'package:starrit/feed/service.dart';
import 'package:starrit/common/util/json.dart';
import 'package:starrit/access/model/access.dart';

final Epic<AppState> feedEpic = combineEpics([
  _loadData,
]);

Stream<dynamic> _loadData(Stream<dynamic> actions, EpicStore<AppState> store) {
  Future<dynamic> fetch(LoadFeedData request) async {
    try {
      final response = await listing(
        baseUrl: store.state.access.baseUrl,
        feed: request.feed,
        after: request.after,
      );
      if (response.statusCode != 200) {
        throw HttpException(
            'Usuccessful feed request. Code: ${response.statusCode}');
      }
      final Map<String, dynamic> result = jsonDecode(response.body);
      final posts = result
          .get<List>('data.children')
          .map((json) => Post.fromJson(json['data']))
          .toList();
      final next = result.string('data.after');

      return LoadFeedDataSuccess(request.feed, posts, next);
    } on Exception catch (e) {
      return LoadFeedDataFailure(request.feed, e);
    }
  }

  Stream<dynamic> dispose(LoadFeedData request) => actions.where((action) =>
      (action is LoadFeedData && action.feed == request.feed) ||
      (action is DisposeFeedData && action.feed == request.feed));

  return actions.whereType<LoadFeedData>().switchMap(
      (request) => fetch(request).asStream().takeUntil(dispose(request)));
}
