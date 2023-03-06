
import 'media_clipboard_platform_interface.dart';

class MediaClipboard {
  Future<String?> getPlatformVersion() {
    return MediaClipboardPlatform.instance.getPlatformVersion();
  }
}
