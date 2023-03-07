import 'dart:async';

import 'package:flutter/services.dart';

class MediaClipboard {
  static const MethodChannel _channel = MethodChannel('com.dholasain.media_plugin');

  static Future<void> copyImageToClipboard(String base64Data) async {
    try {
      final Map<String, dynamic> params = {'base64Data': base64Data, 'mimeType': 'image/png'};
      await _channel.invokeMethod('copyImageToClipboard', params);
    } on PlatformException catch (e) {
      print(e.message);
      // throw e.message;
    }
  }

  static Future<void> copyGifToClipboard(String base64Data) async {
    try {
      final Map<String, dynamic> params = {'base64Data': base64Data, 'mimeType': 'image/gif'};
      await _channel.invokeMethod('copyGifToClipboard', params);
    } on PlatformException catch (e) {
      print(e.message);
    }
  }

  static Future<void> copyWebpToClipboard(String base64Data) async {
    try {
      final Map<String, dynamic> params = {'base64Data': base64Data, 'mimeType': 'image/webp'};
      await _channel.invokeMethod('copyWebpToClipboard', params);
    } on PlatformException catch (e) {
      print(e.message);
    }
  }
}
