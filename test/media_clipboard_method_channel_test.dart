import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:media_clipboard/media_clipboard_method_channel.dart';

void main() {
  MethodChannelMediaClipboard platform = MethodChannelMediaClipboard();
  const MethodChannel channel = MethodChannel('media_clipboard');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
