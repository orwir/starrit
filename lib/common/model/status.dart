/// Determines status of a state.
enum StateStatus {
  /// Loading, waiting, doing something.
  processing,

  /// Process successfully completed.
  success,

  /// Process completed with an error.
  failure,
}
