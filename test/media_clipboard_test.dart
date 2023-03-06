import 'package:flutter_test/flutter_test.dart';
import 'package:media_clipboard/media_clipboard.dart';
import 'package:media_clipboard/media_clipboard_platform_interface.dart';
import 'package:media_clipboard/media_clipboard_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockMediaClipboardPlatform
    with MockPlatformInterfaceMixin
    implements MediaClipboardPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final MediaClipboardPlatform initialPlatform = MediaClipboardPlatform.instance;

  test('$MethodChannelMediaClipboard is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelMediaClipboard>());
  });

  test('getPlatformVersion', () async {
    MediaClipboard mediaClipboardPlugin = MediaClipboard();
    MockMediaClipboardPlatform fakePlatform = MockMediaClipboardPlatform();
    MediaClipboardPlatform.instance = fakePlatform;

    expect(await mediaClipboardPlugin.getPlatformVersion(), '42');
  });
}
