package com.example.kotlin_1.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.kotlin_1.Service.ForegroundService

class ServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var handle = intent?.getIntExtra("handle", 0)
        var newIntent = Intent(context, ForegroundService::class.java)
        newIntent.putExtra("handle", handle)
        context?.startService(newIntent)
    }
}