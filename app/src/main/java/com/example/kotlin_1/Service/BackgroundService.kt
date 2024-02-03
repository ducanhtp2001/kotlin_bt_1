package com.example.kotlin_1.Service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Debug
import android.os.IBinder
import android.util.Log
import com.example.kotlin_1.R
import kotlinx.coroutines.delay

class BackgroundService: Service() {
    private val TAG : String = "MyService"
    private lateinit var player: MediaPlayer

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service is running!")
        player = MediaPlayer()
        val resourceId = R.raw.pirates_of_the_caribbean
        val fileDescriptor = resources.openRawResourceFd(resourceId)
        if (fileDescriptor != null) {
            player.setDataSource(fileDescriptor.fileDescriptor, fileDescriptor.startOffset, fileDescriptor.length)
            fileDescriptor.close()

            player.prepare()
            player.start()
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
        Log.d(TAG, "Service is stop")
    }
}