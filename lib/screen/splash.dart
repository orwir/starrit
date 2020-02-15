import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:starrit/action/main.dart';
import 'package:starrit/model/state.dart';

class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return StoreConnector<AppState, bool>(
      distinct: true,
      converter: (store) => store.state?.feed != null,
      onInit: (store) => store.dispatch(LoadLatestFeedAction()),
      onDidChange: (loaded) => {
        if (loaded)
          {
            Navigator.pushNamedAndRemoveUntil(
              context,
              '/posts',
              ModalRoute.withName('/'),
            )
          }
      },
      builder: (context, _) {
        return Scaffold(
          body: Stack(
            children: <Widget>[
              Container(
                alignment: Alignment.center,
                child: Image.asset('assets/images/splash.png'),
              ),
              Container(
                alignment: Alignment.bottomCenter,
                padding: EdgeInsets.only(bottom: 16.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: <Widget>[
                    Text('Developed by', style: _descriptionStyle(context)),
                    Text('orwir', style: _developerStyle(context)),
                  ],
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  TextStyle _descriptionStyle(BuildContext context) =>
      Theme.of(context).textTheme.subhead;

  TextStyle _developerStyle(BuildContext context) =>
      _descriptionStyle(context).copyWith(
        fontWeight: FontWeight.bold,
        color: Theme.of(context).primaryColor,
      );
}
