package com.example.kotlin_1.Service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BoundService: Service(){
    val myBinder = MyBinder()
    val TAG = "BoundService"

    var number1: Int = 0
    var number2: Int = 0

    fun outputResult(): Int {
        return number1 + number2
    }

    inner class MyBinder: Binder() {
        public fun getService(): BoundService = this@BoundService
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "on bind")
        return myBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "unbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}