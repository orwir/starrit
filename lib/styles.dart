import 'package:flutter/material.dart';

ThemeData lightTheme = ThemeData(
  brightness: Brightness.light,
  textTheme: _textTheme,
);

ThemeData darkTheme = ThemeData(
  brightness: Brightness.dark,
  textTheme: _textTheme,
);

TextTheme _textTheme = TextTheme();
