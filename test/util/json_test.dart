import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/util/json.dart';

void main() {
  group('Json.path', () {
    test(
      'should return string "value" when calls path("key") on {key: "value"}',
      () {
        const json = {'key': 'value'};
        expect(json.path('key'), 'value');
      },
    );
    test(
      'should return int 42 when calls path("key") on {key: 42}',
      () {
        const json = {'key': 42};
        expect(json.path('key'), 42);
      },
    );
    test(
      'should return List [1,2,3] when calls path("key") on {key: [1,2,3]}',
      () {
        const json = {
          'key': [1, 2, 3]
        };
        expect(json.path('key'), [1, 2, 3]);
      },
    );
    test(
      'should return null when calls path("key") on {}',
      () {
        const json = <String, dynamic>{};
        expect(json.path('key'), null);
      },
    );
    test(
      'should return default value 42 when calls path("key", def: 42) on {}',
      () {
        const json = <String, dynamic>{};
        expect(json.path('key', def: 42), 42);
      },
    );
    test(
      'should return "value" when calls path("key.inner") on {key: {inner: "value"}}',
      () {
        const json = {
          'key': {'inner': 'value'}
        };
        expect(json.path('key.inner'), 'value');
      },
    );
    test(
      'should return null when calls path("path.not.found") on {path: {inner: "value"}}',
      () {
        const json = {
          'path': {'inner': 'value'}
        };
        expect(json.path('path.not.found'), null);
      },
    );
    test(
      'should return 2 when calls path("key[1]") on {key: [1,2,3]}',
      () {
        const json = {
          'key': [1, 2, 3]
        };
        expect(json.path('key[1]'), 2);
      },
    );
    test(
      'should return "value" when calls path("key[0].inner" on {key: [{inner: "value"}]}',
      () {
        const json = {
          'key': [
            {'inner': 'value'}
          ]
        };
        expect(json.path('key[0].inner'), 'value');
      },
    );
  });
}
