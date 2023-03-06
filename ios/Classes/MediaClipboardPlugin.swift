import Flutter
import UIKit

public class MediaClipboardPlugin: NSObject, FlutterPlugin {

  // Start of my code

      
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "media_clipboard", binaryMessenger: registrar.messenger())
    let instance = SwiftClipboardPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
      case "copyImage":
        if let imagePath = call.arguments as? String {
          copyImageToClipboard(imagePath: imagePath)
          result(nil)
        }
      default:
        result(FlutterMethodNotImplemented)
    }
  }

  private func copyImageToClipboard(imagePath: String) {
    let url = URL(string: imagePath)!
    let imageData = try! Data(contentsOf: url)
    let image = UIImage(data: imageData)!
    UIPasteboard.general.image = image
  }


  // End of my code


  //? Below is the defualt code that is generated when you create a new plugin
  // public static func register(with registrar: FlutterPluginRegistrar) {
  //   let channel = FlutterMethodChannel(name: "media_clipboard", binaryMessenger: registrar.messenger())
  //   let instance = MediaClipboardPlugin()
  //   registrar.addMethodCallDelegate(instance, channel: channel)
  // }

  // public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
  //   result("iOS " + UIDevice.current.systemVersion)
  // }
}
