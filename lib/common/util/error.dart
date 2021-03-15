Exception normalize(dynamic error) {
  if (error is Exception) return error;
  if (error is String) return Exception(error);
  return Exception(error.toString());
}
