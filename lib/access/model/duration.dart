enum Duration { temporary, permanent }

extension DurationExtensions on Duration {
  String get label => toString().split('.')[1];
}
