import 'dart:io';

import 'package:http/http.dart' as http;
import 'package:redux/redux.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/app/state.dart';

/// Wrapper for HttpClient.
///
/// * Throws [HttpException] if response code is not 200.
/// * Controls base link depends on [Access] of a user.
/// * Set common headers such as user-agent and authorization.
/// * Provides functionality to refresh access token when it expires.
class Network {
  final Store<AppState> Function() _storeHolder;
  Store<AppState> get _store => _storeHolder();

  Network(this._storeHolder) : assert(_storeHolder != null);

  Future<http.Response> get(dynamic url, {Map<String, String> headers}) async {
    if (url is String && url.startsWith('/')) {
      url = _store.state.access.baseUrl + url;
    }
    // TODO: implement authentication functionality.
    final response = await http.get(url, headers: {
      ...?headers,
    });
    if (response.statusCode != 200) {
      throw HttpException(response.reasonPhrase);
    }
    return response;
  }
}
