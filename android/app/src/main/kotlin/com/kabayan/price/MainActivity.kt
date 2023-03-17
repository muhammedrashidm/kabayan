package com.kabayan.price

import io.flutter.embedding.android.FlutterActivity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.content.Intent
import android.content.IntentFilter
import com.kabayan.price_checker.NativeMethodChannel
import com.kabayan.price_checker.ScannerReceiver

class MainActivity: FlutterActivity() {
    private val CHANNEL = "scannerChannel"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "configureScanner") {
                configureScanner()
            }else if (call.method == "testData") {
                testData();
            }
        }
        NativeMethodChannel.configureChannel(flutterEngine)
    }

    private fun configureScanner() {
        val intent = Intent("nlscan.action.SCANNER_TRIG")
        sendBroadcast(intent)


        var receiver = ScannerReceiver()
        val filter = IntentFilter()
        filter.addAction("nlscan.action.SCANNER_TRIG")
        registerReceiver(receiver, filter)
    }
    private  fun  testData(){
        val intent = Intent()
        intent.action = "nlscan.action.SCANNER_TRIG"
        intent.putExtra("message", getRandomString(5))
        sendBroadcast(intent)
    }

    private fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

}
