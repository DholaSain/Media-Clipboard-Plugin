package com.dholasain.media_clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class MediaClipboardPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, PluginRegistry.RequestPermissionsResultListener {

    private static final String CHANNEL_NAME = "media_clipboard";

    private Context mContext;
    private MethodChannel mChannel;
    private ClipboardManager mClipboardManager;

    private static final int REQUEST_CODE = 999;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        mContext = flutterPluginBinding.getApplicationContext();
        mChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
        mChannel.setMethodCallHandler(this);
        mClipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "copyToClipboard":
                String filePath = call.argument("filePath");
                String fileType = call.argument("fileType");
                try {
                    copyFileToClipboard(filePath, fileType);
                    result.success(true);
                } catch (IOException e) {
                    result.success(false);
                }
                break;
            default:
                result.notImplemented();
        }
    }

    private void copyFileToClipboard(String filePath, String fileType) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found");
        }

        Uri uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file);
        ClipData clipData = ClipData.newUri(mContext.getContentResolver(), fileType, uri);

        mClipboardManager.setPrimaryClip(clipData);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        mContext = null;
        mChannel.setMethodCallHandler(null);
        mClipboardManager = null;
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        return false;
    }
}
