/// Determine status of specific state.
enum StateStatus {
  /// Data has never requested.
  initial,

  /// Started process of data obtaining.
  loading,

  /// Data successfully obtained.
  success,

  /// Data obtain completed with an error.
  failure,
}
