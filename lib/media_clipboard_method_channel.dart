import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'media_clipboard_platform_interface.dart';

/// An implementation of [MediaClipboardPlatform] that uses method channels.
class MethodChannelMediaClipboard extends MediaClipboardPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('media_clipboard');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
