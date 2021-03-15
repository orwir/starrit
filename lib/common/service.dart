import 'package:shared_preferences/shared_preferences.dart';
import 'package:starrit/common/network.dart';
import 'package:starrit/feed/service.dart';
import 'package:starrit/search/service.dart';
import 'package:starrit/settings/service.dart';

/// Container for low-level functions.
///
/// Functionality extends by mixins.
/// Provides an access to data sources and computations.
/// Such as network, storage, etc.
abstract class Service {
  final SharedPreferences sharedPreferences;
  final Network network;

  Service(this.sharedPreferences, this.network)
      : assert(sharedPreferences != null),
        assert(network != null);
}

class StarritService extends Service
    with SettingsService, FeedService, SearchService {
  StarritService(SharedPreferences prefs, Network network)
      : super(prefs, network);
}
