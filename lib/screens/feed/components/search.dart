import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:starrit/models/feed.dart';
import 'package:starrit/models/subreddit.dart';
import 'package:starrit/services/feed.dart';

class FeedSearch extends SearchDelegate<Feed> {
  @override
  List<Widget> buildActions(BuildContext context) {
    return [];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: Icon(Icons.arrow_back),
      onPressed: () => close(context, null),
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    return FutureBuilder<Iterable<Subreddit>>(
      builder: (context, snapshot) => ListView(
        children: <Widget>[],
      ),
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    return FutureBuilder<Iterable<String>>(
      future: _loadSuggestions(query),
      builder: (context, snapshot) {
        return ListView(
          children: [
            if (snapshot.hasData)
              ...snapshot.data
                  .map(
                    (suggestion) => ListTile(
                      title: Text(suggestion),
                      onTap: () {
                        query = suggestion;
                        showResults(context);
                      },
                    ),
                  )
                  .toList(),
          ],
        );
      },
    );
  }

  Timer _debounce;
  Completer<Iterable<String>> _completer;
  Future<Iterable<String>> _loadSuggestions(String query) async {
    _debounce?.cancel();
    if (_completer == null || _completer.isCompleted) {
      _completer = Completer();
    }

    _debounce = Timer(Duration(milliseconds: 500), () async {
      Iterable<String> result = const [];
      if (query.length < 3) {
        result = Type.values.map((t) => t.label);
      } else {
        final response = await suggestedSubreddits(
          domain: 'reddit.com',
          query: query,
        );
        if (response.statusCode == 200) {
          result = List.from(jsonDecode(response.body)['names']);
        }
      }
      if (!_completer.isCompleted) {
        _completer.complete(result);
      }
    });
    return _completer.future;
  }
}
