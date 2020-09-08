/// Preferences keys.
enum Pref { blurNsfw, lastFeed, access }

extension PrefExtension on Pref {
  /// String representation of preference key.
  String get key => toString();
}
