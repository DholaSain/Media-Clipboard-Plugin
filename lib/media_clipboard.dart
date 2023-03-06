import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

// import 'media_clipboard_platform_interface.dart';

// class MediaClipboard {
//   Future<String?> getPlatformVersion() {
//     return MediaClipboardPlatform.instance.getPlatformVersion();
//   }
// }
class ClipboardPlugin {
  static const MethodChannel _channel = MethodChannel('media_clipboard');

  static Future<void> copyImage(String imagePath) async {
    try {
      await _channel.invokeMethod('copyImage', {'imagePath': imagePath});
    } on PlatformException catch (e) {
      if (kDebugMode) {
        print('Failed to copy image: ${e.message}');
      }
    }
  }
}
