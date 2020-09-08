import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class AccessBanner extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // final store = StoreProvider.of<AppState>(context);
    return MaterialBanner(
      content: Text(
        'To personalize your experience you can authorize. Or stay anonymous.',
      ),
      actions: [
        FlatButton(
          child: Text('Anonymous'),
          onPressed: () => {/* TODO: anonymous access flow */},
        ),
        FlatButton(
          child: Text('Authorize'),
          onPressed: () => {/* TODO: authorized access flow */},
        ),
      ],
    );
  }
}
