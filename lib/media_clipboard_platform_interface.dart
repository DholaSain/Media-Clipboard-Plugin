import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'media_clipboard_method_channel.dart';

abstract class MediaClipboardPlatform extends PlatformInterface {
  /// Constructs a MediaClipboardPlatform.
  MediaClipboardPlatform() : super(token: _token);

  static final Object _token = Object();

  static MediaClipboardPlatform _instance = MethodChannelMediaClipboard();

  /// The default instance of [MediaClipboardPlatform] to use.
  ///
  /// Defaults to [MethodChannelMediaClipboard].
  static MediaClipboardPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MediaClipboardPlatform] when
  /// they register themselves.
  static set instance(MediaClipboardPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
