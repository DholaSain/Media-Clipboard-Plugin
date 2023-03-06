package com.dholasain.media_clipboard;

// Start of my implementation
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;
// End of my implementation

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** MediaClipboardPlugin */
public class MediaClipboardPlugin implements FlutterPlugin, MethodCallHandler {

  // Start of my implementation

  private static final String CHANNEL_NAME = "media_clipboard";
  private final PluginRegistry.Registrar registrar;

  public static void registerWith(PluginRegistry.Registrar registrar) {
    MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL_NAME);
    ClipboardPlugin instance = new ClipboardPlugin(registrar);
    channel.setMethodCallHandler(instance);
  }

  private ClipboardPlugin(PluginRegistry.Registrar registrar) {
    this.registrar = registrar;
  }

  @Override
  public void onMethodCall(MethodCall call, MethodChannel.Result result) {
    switch (call.method) {
      case "copyImage":
        String imagePath = call.argument("imagePath");
        copyImageToClipboard(imagePath);
        result.success(null);
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  private void copyImageToClipboard(String imagePath) {
    try {
      Context context = registrar.context();
      ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(imagePath));
      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      ClipData clip = ClipData.newPlainText("image", "");
      clip.addItem(new ClipData.Item(bitmap));
      clipboard.setPrimaryClip(clip);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  // End of my implementation


  //? Below is the default plugin code. I have commented it out.
  // /// The MethodChannel that will the communication between Flutter and native Android
  // ///
  // /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  // /// when the Flutter Engine is detached from the Activity
  // private MethodChannel channel;

  // @Override
  // public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
  //   channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "media_clipboard");
  //   channel.setMethodCallHandler(this);
  // }

  // @Override
  // public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
  //   if (call.method.equals("getPlatformVersion")) {
  //     result.success("Android " + android.os.Build.VERSION.RELEASE);
  //   } else {
  //     result.notImplemented();
  //   }
  // }

  // @Override
  // public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
  //   channel.setMethodCallHandler(null);
  // }
}
