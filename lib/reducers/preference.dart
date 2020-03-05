import 'package:starrit/actions/preference.dart';
import 'package:starrit/models/state.dart';

AppState reducer(AppState state, dynamic action) {
  if (action is BlurNsfwChangeAction) {
    return state.copyWith(
      blurNfsw: action.blurNsfw,
    );
  }

  return state;
}
