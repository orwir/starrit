import 'dart:io';

import 'package:http/http.dart' as http;
import 'package:redux/redux.dart';
import 'package:starrit/access/model/access.dart';
import 'package:starrit/common/model/state.dart';

/// Wrapper for HttpClient.
///
/// * Throws [HttpException] if response code is not 200.
/// * Adds base url based on [Access] level of a user if [url] is a string
///   and starts with '/'.
/// * Sets common headers such as user-agent and authorization.
/// * Provides functionality to refresh access token when current one expires.
class Network {
  final Store<AppState> Function() _storeHolder;
  Store<AppState> get _store => _storeHolder();

  Network(this._storeHolder) : assert(_storeHolder != null);

  Future<http.Response> get(dynamic url, {Map<String, String> headers}) async {
    if (url is String && url.startsWith('/')) {
      url = _store.state.access.baseUrl + url;
    }
    // TODO: implement authentication functionality.
    // TODO: add user-agent
    final response = await http.get(
      url is Uri ? url : Uri.parse(url),
      headers: {...?headers},
    );
    if (response.statusCode != 200) {
      throw HttpException(response.reasonPhrase);
    }
    return response;
  }
}
