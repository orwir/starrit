import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:starrit/access/thunks.dart';
import 'package:starrit/common/model/state.dart';
import 'package:uuid/uuid.dart';

final _uuid = Uuid();

class AccessBanner extends StatelessWidget {
  @override
  Widget build(BuildContext context) =>
      StoreConnector<AppState, Store<AppState>>(
        converter: (store) => store,
        builder: (context, store) => MaterialBanner(
          content: Text(
            'To personalize your experience you can authorize. Or stay anonymous.',
          ),
          actions: [
            FlatButton(
              child: Text('Anonymous'),
              onPressed: () => store.dispatch(setAnonymousAccess()),
            ),
            FlatButton(
              child: Text('Authorize'),
              onPressed: () => store.dispatch(startAuthorization(_uuid.v1())),
            ),
          ],
        ),
      );
}
