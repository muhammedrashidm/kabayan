package com.kabayan.price_checker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class ScannerReceiver : BroadcastReceiver() {
    override fun onReceive(myContext: Context?, intent: Intent?) {
        val action: String? = intent?.action
//        if ("nlscan.action.SCANNER_TRIG" == action) {
//            val message: String = intent.getStringExtra("message") as String
//            NativeMethodChannel.sendData(message)
//            Log.d("MyReceiver 12", "Received message: $message")
//        }
        if (intent != null) {
            val barcode = intent.getStringExtra("SCAN_BARCODE1")
            val barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1)
            if (barcode != null) {
                NativeMethodChannel.sendData(barcode)
            }
        }
    }
}