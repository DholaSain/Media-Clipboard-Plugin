package com.dholasain.media_clipboard

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** MediaClipboardPlugin */
class MediaClipboardPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private lateinit var mediaClipboard: MediaClipboardPlugin

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "media_clipboard")
        channel.setMethodCallHandler(this)
        mediaClipboard = MediaClipboardPlugin(flutterPluginBinding.applicationContext)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "copyToClipboard" -> {
                val data = call.argument<ByteArray>("data")
                val mimeType = call.argument<String>("mimeType")
                if (data != null && mimeType != null) {
                    mediaClipboard.copyMediaToClipboard(data, mimeType)
                    result.success(null)
                } else {
                    result.error("INVALID_ARGUMENTS", "Data and mimeType arguments are required", null)
                }
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
