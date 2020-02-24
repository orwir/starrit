import 'package:flutter_test/flutter_test.dart';
import 'package:starrit/extensions/json.dart';

void main() {
  group('Json.get', () {
    test(
      'should return string "value" when calls get("key") on {key: "value"}',
      () {
        const json = {'key': 'value'};
        expect(json.get('key'), 'value');
      },
    );
    test(
      'should return int 42 when calls get("key") on {key: 42}',
      () {
        const json = {'key': 42};
        expect(json.get('key'), 42);
      },
    );
    test(
      'should return List [1,2,3] when calls get("key") on {key: [1,2,3]}',
      () {
        const json = {
          'key': [1, 2, 3]
        };
        expect(json.get('key'), [1, 2, 3]);
      },
    );
    test(
      'should return null when calls get("key") on {}',
      () {
        const json = <String, dynamic>{};
        expect(json.get('key'), null);
      },
    );
    test(
      'should return default value 42 when calls get("key", def: 42) on {}',
      () {
        const json = <String, dynamic>{};
        expect(json.get('key', def: 42), 42);
      },
    );
    test(
      'should return "value" when calls get("key.inner") on {key: {inner: "value"}}',
      () {
        const json = {
          'key': {'inner': 'value'}
        };
        expect(json.get('key.inner'), 'value');
      },
    );
    test(
      'should return null when calls get("path.not.found") on {path: {inner: "value"}}',
      () {
        const json = {
          'path': {'inner': 'value'}
        };
        expect(json.get('path.not.found'), null);
      },
    );
    test(
      'should return 2 when calls get("key[1]") on {key: [1,2,3]}',
      () {
        const json = {
          'key': [1, 2, 3]
        };
        expect(json.get('key[1]'), 2);
      },
    );
    test(
      'should return "value" when calls get("key[0].inner" on {key: [{inner: "value"}]}',
      () {
        const json = {
          'key': [
            {'inner': 'value'}
          ]
        };
        expect(json.get('key[0].inner'), 'value');
      },
    );
  });

  group('Json.string', () {
    test(
      'should return "value" when calls string("key") on {key: "value"}',
      () {
        const json = {'key': 'value'};
        expect(json.string('key'), 'value');
      },
    );
    test(
      'should return null when calls string("key") on {key: " "}',
      () {
        const json = {'key': ' '};
        expect(json.string('key'), null);
      },
    );
    test(
      'should return null when calls string("key", nonBlank: false) on {key: " "}',
      () {
        const json = {'key': ' '};
        expect(json.string('key', nonBlank: false), ' ');
      },
    );
  });
}
