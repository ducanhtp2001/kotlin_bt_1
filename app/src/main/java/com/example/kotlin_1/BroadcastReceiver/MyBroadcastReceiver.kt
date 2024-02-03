package com.example.kotlin_1.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("wifi", "on receiver")

        if (intent?.action == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
            val networkInfo: NetworkInfo? = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO)
            if (networkInfo?.type == ConnectivityManager.TYPE_WIFI) {
                when (networkInfo.state) {
                    NetworkInfo.State.CONNECTED -> {
                        Toast.makeText(context, " Wi-Fi is connected", Toast.LENGTH_SHORT).show()
                    }
                    NetworkInfo.State.DISCONNECTED -> {
                        Toast.makeText(context, " Wi-Fi is disconnected", Toast.LENGTH_SHORT).show()
                    }
                    else ->{}
                }
            }
        }
    }
}