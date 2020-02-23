import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class StarritLocalizations {
  final Locale locale;

  StarritLocalizations(this.locale);

  static List<Locale> supported = const [Locale('en')];

  static StarritLocalizations of(BuildContext context) {
    return Localizations.of(context, StarritLocalizations);
  }

  static const _StarritLocalizationsDelegate delegate =
      _StarritLocalizationsDelegate();
}

class _StarritLocalizationsDelegate extends LocalizationsDelegate {
  const _StarritLocalizationsDelegate();

  @override
  bool isSupported(Locale locale) {
    return StarritLocalizations.supported.contains(locale);
  }

  @override
  Future<StarritLocalizations> load(Locale locale) async {
    Intl.defaultLocale = Intl.canonicalizedLocale(locale.toString());
    return StarritLocalizations(locale);
  }

  @override
  bool shouldReload(_StarritLocalizationsDelegate old) => false;
}
