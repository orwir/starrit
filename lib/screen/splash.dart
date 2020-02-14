import 'package:flutter/material.dart';

class SplashScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final developerStyle = theme.textTheme.subhead.copyWith(
      fontWeight: FontWeight.bold,
      color: theme.primaryColor,
    );
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
                Text(
                  'Developed by',
                  style: theme.textTheme.subhead,
                ),
                Text(
                  'orwir',
                  style: developerStyle,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
