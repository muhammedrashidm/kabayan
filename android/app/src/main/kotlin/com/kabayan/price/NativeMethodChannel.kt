package com.kabayan.price_checker
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

object NativeMethodChannel {
    private const val CHANNEL_NAME = "scannerChannel"
    private lateinit var methodChannel: MethodChannel

    fun configureChannel(flutterEngine: FlutterEngine) {
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL_NAME)
    }
    fun sendData(id: String) {
        methodChannel.invokeMethod("sendData", id)
    }
}