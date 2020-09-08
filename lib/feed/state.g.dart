// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'state.dart';

// **************************************************************************
// BuiltValueGenerator
// **************************************************************************

class _$FeedState extends FeedState {
  @override
  final Status status;
  @override
  final Exception exception;
  @override
  final BuiltList<Post> posts;
  @override
  final String next;

  factory _$FeedState([void Function(FeedStateBuilder) updates]) =>
      (new FeedStateBuilder()..update(updates)).build();

  _$FeedState._({this.status, this.exception, this.posts, this.next})
      : super._() {
    if (status == null) {
      throw new BuiltValueNullFieldError('FeedState', 'status');
    }
    if (posts == null) {
      throw new BuiltValueNullFieldError('FeedState', 'posts');
    }
  }

  @override
  FeedState rebuild(void Function(FeedStateBuilder) updates) =>
      (toBuilder()..update(updates)).build();

  @override
  FeedStateBuilder toBuilder() => new FeedStateBuilder()..replace(this);

  @override
  bool operator ==(Object other) {
    if (identical(other, this)) return true;
    return other is FeedState &&
        status == other.status &&
        exception == other.exception &&
        posts == other.posts &&
        next == other.next;
  }

  @override
  int get hashCode {
    return $jf($jc(
        $jc($jc($jc(0, status.hashCode), exception.hashCode), posts.hashCode),
        next.hashCode));
  }
}

class FeedStateBuilder implements Builder<FeedState, FeedStateBuilder> {
  _$FeedState _$v;

  Status _status;
  Status get status => _$this._status;
  set status(Status status) => _$this._status = status;

  Exception _exception;
  Exception get exception => _$this._exception;
  set exception(Exception exception) => _$this._exception = exception;

  ListBuilder<Post> _posts;
  ListBuilder<Post> get posts => _$this._posts ??= new ListBuilder<Post>();
  set posts(ListBuilder<Post> posts) => _$this._posts = posts;

  String _next;
  String get next => _$this._next;
  set next(String next) => _$this._next = next;

  FeedStateBuilder() {
    FeedState._initializeBuilder(this);
  }

  FeedStateBuilder get _$this {
    if (_$v != null) {
      _status = _$v.status;
      _exception = _$v.exception;
      _posts = _$v.posts?.toBuilder();
      _next = _$v.next;
      _$v = null;
    }
    return this;
  }

  @override
  void replace(FeedState other) {
    if (other == null) {
      throw new ArgumentError.notNull('other');
    }
    _$v = other as _$FeedState;
  }

  @override
  void update(void Function(FeedStateBuilder) updates) {
    if (updates != null) updates(this);
  }

  @override
  _$FeedState build() {
    _$FeedState _$result;
    try {
      _$result = _$v ??
          new _$FeedState._(
              status: status,
              exception: exception,
              posts: posts.build(),
              next: next);
    } catch (_) {
      String _$failedField;
      try {
        _$failedField = 'posts';
        posts.build();
      } catch (e) {
        throw new BuiltValueNestedFieldError(
            'FeedState', _$failedField, e.toString());
      }
      rethrow;
    }
    replace(_$result);
    return _$result;
  }
}

// ignore_for_file: always_put_control_body_on_new_line,always_specify_types,annotate_overrides,avoid_annotating_with_dynamic,avoid_as,avoid_catches_without_on_clauses,avoid_returning_this,lines_longer_than_80_chars,omit_local_variable_types,prefer_expression_function_bodies,sort_constructors_first,test_types_in_equals,unnecessary_const,unnecessary_new
