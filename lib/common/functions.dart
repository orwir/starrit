import 'package:redux/redux.dart';
import 'package:starrit/common/model/state.dart';
import 'package:starrit/env.dart';

void handleLink(Store<AppState> store, String link) {
  if (link.startsWith(Config.authResponseUri)) {
    // TODO: dispatch auth response uri processing
  }
}
