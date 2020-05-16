enum TokenDuration { temporary, permanent }

extension DurationExtensions on TokenDuration {
  String get label => toString().split('.')[1];
  String get parameter => label;
}
