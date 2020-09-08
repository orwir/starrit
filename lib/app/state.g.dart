// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'state.dart';

// **************************************************************************
// BuiltValueGenerator
// **************************************************************************

class _$AppState extends AppState {
  @override
  final Status status;
  @override
  final Exception exception;
  @override
  final Access access;
  @override
  final Feed lastFeed;
  @override
  final bool blurNsfw;
  @override
  final BuiltMap<Feed, FeedState> feeds;
  @override
  final BuiltList<FeedType> searchSuggestions;
  @override
  final FeedSort searchSort;

  factory _$AppState([void Function(AppStateBuilder) updates]) =>
      (new AppStateBuilder()..update(updates)).build();

  _$AppState._(
      {this.status,
      this.exception,
      this.access,
      this.lastFeed,
      this.blurNsfw,
      this.feeds,
      this.searchSuggestions,
      this.searchSort})
      : super._() {
    if (status == null) {
      throw new BuiltValueNullFieldError('AppState', 'status');
    }
    if (access == null) {
      throw new BuiltValueNullFieldError('AppState', 'access');
    }
    if (lastFeed == null) {
      throw new BuiltValueNullFieldError('AppState', 'lastFeed');
    }
    if (blurNsfw == null) {
      throw new BuiltValueNullFieldError('AppState', 'blurNsfw');
    }
    if (feeds == null) {
      throw new BuiltValueNullFieldError('AppState', 'feeds');
    }
    if (searchSuggestions == null) {
      throw new BuiltValueNullFieldError('AppState', 'searchSuggestions');
    }
    if (searchSort == null) {
      throw new BuiltValueNullFieldError('AppState', 'searchSort');
    }
  }

  @override
  AppState rebuild(void Function(AppStateBuilder) updates) =>
      (toBuilder()..update(updates)).build();

  @override
  AppStateBuilder toBuilder() => new AppStateBuilder()..replace(this);

  @override
  bool operator ==(Object other) {
    if (identical(other, this)) return true;
    return other is AppState &&
        status == other.status &&
        exception == other.exception &&
        access == other.access &&
        lastFeed == other.lastFeed &&
        blurNsfw == other.blurNsfw &&
        feeds == other.feeds &&
        searchSuggestions == other.searchSuggestions &&
        searchSort == other.searchSort;
  }

  @override
  int get hashCode {
    return $jf($jc(
        $jc(
            $jc(
                $jc(
                    $jc(
                        $jc($jc($jc(0, status.hashCode), exception.hashCode),
                            access.hashCode),
                        lastFeed.hashCode),
                    blurNsfw.hashCode),
                feeds.hashCode),
            searchSuggestions.hashCode),
        searchSort.hashCode));
  }

  @override
  String toString() {
    return (newBuiltValueToStringHelper('AppState')
          ..add('status', status)
          ..add('exception', exception)
          ..add('access', access)
          ..add('lastFeed', lastFeed)
          ..add('blurNsfw', blurNsfw)
          ..add('feeds', feeds)
          ..add('searchSuggestions', searchSuggestions)
          ..add('searchSort', searchSort))
        .toString();
  }
}

class AppStateBuilder implements Builder<AppState, AppStateBuilder> {
  _$AppState _$v;

  Status _status;
  Status get status => _$this._status;
  set status(Status status) => _$this._status = status;

  Exception _exception;
  Exception get exception => _$this._exception;
  set exception(Exception exception) => _$this._exception = exception;

  Access _access;
  Access get access => _$this._access;
  set access(Access access) => _$this._access = access;

  Feed _lastFeed;
  Feed get lastFeed => _$this._lastFeed;
  set lastFeed(Feed lastFeed) => _$this._lastFeed = lastFeed;

  bool _blurNsfw;
  bool get blurNsfw => _$this._blurNsfw;
  set blurNsfw(bool blurNsfw) => _$this._blurNsfw = blurNsfw;

  MapBuilder<Feed, FeedState> _feeds;
  MapBuilder<Feed, FeedState> get feeds =>
      _$this._feeds ??= new MapBuilder<Feed, FeedState>();
  set feeds(MapBuilder<Feed, FeedState> feeds) => _$this._feeds = feeds;

  ListBuilder<FeedType> _searchSuggestions;
  ListBuilder<FeedType> get searchSuggestions =>
      _$this._searchSuggestions ??= new ListBuilder<FeedType>();
  set searchSuggestions(ListBuilder<FeedType> searchSuggestions) =>
      _$this._searchSuggestions = searchSuggestions;

  FeedSort _searchSort;
  FeedSort get searchSort => _$this._searchSort;
  set searchSort(FeedSort searchSort) => _$this._searchSort = searchSort;

  AppStateBuilder();

  AppStateBuilder get _$this {
    if (_$v != null) {
      _status = _$v.status;
      _exception = _$v.exception;
      _access = _$v.access;
      _lastFeed = _$v.lastFeed;
      _blurNsfw = _$v.blurNsfw;
      _feeds = _$v.feeds?.toBuilder();
      _searchSuggestions = _$v.searchSuggestions?.toBuilder();
      _searchSort = _$v.searchSort;
      _$v = null;
    }
    return this;
  }

  @override
  void replace(AppState other) {
    if (other == null) {
      throw new ArgumentError.notNull('other');
    }
    _$v = other as _$AppState;
  }

  @override
  void update(void Function(AppStateBuilder) updates) {
    if (updates != null) updates(this);
  }

  @override
  _$AppState build() {
    _$AppState _$result;
    try {
      _$result = _$v ??
          new _$AppState._(
              status: status,
              exception: exception,
              access: access,
              lastFeed: lastFeed,
              blurNsfw: blurNsfw,
              feeds: feeds.build(),
              searchSuggestions: searchSuggestions.build(),
              searchSort: searchSort);
    } catch (_) {
      String _$failedField;
      try {
        _$failedField = 'feeds';
        feeds.build();
        _$failedField = 'searchSuggestions';
        searchSuggestions.build();
      } catch (e) {
        throw new BuiltValueNestedFieldError(
            'AppState', _$failedField, e.toString());
      }
      rethrow;
    }
    replace(_$result);
    return _$result;
  }
}

// ignore_for_file: always_put_control_body_on_new_line,always_specify_types,annotate_overrides,avoid_annotating_with_dynamic,avoid_as,avoid_catches_without_on_clauses,avoid_returning_this,lines_longer_than_80_chars,omit_local_variable_types,prefer_expression_function_bodies,sort_constructors_first,test_types_in_equals,unnecessary_const,unnecessary_new
