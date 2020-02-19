import 'package:starrit/model/state.dart';
import 'package:starrit/reducer/feed.dart' as feed;

AppState reducer(AppState state, dynamic action) {
  return feed.reducer(state, action);
}
