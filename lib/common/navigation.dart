import 'package:flutter/material.dart';

/// Keeps navigation to use it without context.
class Nav {
  static final key = GlobalKey<NavigatorState>();
  static NavigatorState get state => key.currentState;

  Nav._();
}
