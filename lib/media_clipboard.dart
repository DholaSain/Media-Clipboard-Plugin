import 'dart:async';
import 'package:flutter/services.dart';

class MediaClipboard {
  static const MethodChannel _channel = MethodChannel('media_clipboard');

  static Future<void> copyMedia(String path) async {
    try {
      await _channel.invokeMethod('copyMedia', {'path': path});
    } on PlatformException catch (e) {
      throw e.message!;
    }
  }
}
