import 'package:built_collection/built_collection.dart';
import 'package:built_value/built_value.dart';
import 'package:starrit/common/model/status.dart';
import 'package:starrit/feed/model/post.dart';

part 'state.g.dart';

/// State representation for Feed module.
abstract class FeedState implements Built<FeedState, FeedStateBuilder> {
  /// Condition of this [FeedState].
  Status get status;

  /// If [status] is [Status.failure] contains failure cause.
  @nullable
  Exception get exception;

  /// Loaded posts.
  BuiltList<Post> get posts;

  /// ID to obtain next chunk of data.
  @nullable
  String get next;

  @override
  String toString() =>
      '{ status:$status, ${exception != null ? 'exception:$exception,' : ''} '
      'posts:${posts.length}, ${next != null ? 'next$next' : ''}}';

  FeedState._();
  factory FeedState([void Function(FeedStateBuilder) updates]) = _$FeedState;

  static void _initializeBuilder(FeedStateBuilder builder) =>
      builder..status = Status.processing;
}
