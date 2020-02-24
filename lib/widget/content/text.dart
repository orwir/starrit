import 'package:flutter/material.dart';

class TextContent extends StatelessWidget {
  final String text;

  TextContent(this.text);

  @override
  Widget build(BuildContext context) {
    if (text == null) {
      return Container();
    }
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 16),
      child: Text(text),
    );
  }
}
