import 'package:starrit/models/state.dart';

import 'actions.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is BlurNsfwChangeAction) {
    return state.copyWith(
      blurNsfw: action.blurNsfw,
    );
  }

  return state;
}
