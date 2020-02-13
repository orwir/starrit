import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/model/state.dart';
import 'package:starrit/reducer/app.dart';
import 'package:starrit/screen/posts.dart';

main() {
  runApp(StarritApp());
}

class StarritApp extends StatelessWidget {
  final store = Store<AppState>(reducer, initialState: AppState.initial());

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.light(),
      title: 'Stare It!',
      home: StoreProvider(
        store: store,
        child: PostsScreen(),
      ),
    );
  }
}
