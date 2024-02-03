package com.example.kotlin_1.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.kotlin_1.BroadcastReceiver.ServiceReceiver
import com.example.kotlin_1.R
import com.example.kotlin_1.ThirdActivity

class ForegroundService: Service() {
    val handlerPlay = 0
    val handlerPause = 1
    val handlerSkipNext = 3
    val handlerSkipPrevious = 2
    val handlerCancel = 4

    val CHANNEL_ID = "myChannel"

    private val TAG = "ForegroundService"

    private  lateinit var player : MediaPlayer

    var isPlaying = false
    var curentPlaying = 0

    lateinit var channel: NotificationChannel
    lateinit var notificationManager: NotificationManager

    private lateinit var listSong : List<Int>

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        player = MediaPlayer()
        listSong = listOf<Int>(R.raw.pirates_of_the_caribbean, R.raw.potc_sungha_jung)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        var startSong = intent?.getIntExtra("start", -1) as Int
        if(startSong != -1) {
            if (startSong >= listSong.size) {
                curentPlaying = 0
            } else {
                curentPlaying = startSong
            }
            val resourceId = listSong[curentPlaying]
            val fileDescriptor = resources.openRawResourceFd(resourceId)
            if (fileDescriptor != null) {
                player.setDataSource(fileDescriptor.fileDescriptor, fileDescriptor.startOffset, fileDescriptor.length)
                fileDescriptor.close()

                player.prepare()
                player.start()
                isPlaying = true
            }
        }
        var handle = intent.getIntExtra("handle", -1) as Int
        if(handle != -1) {
            when(handle) {
                handlerPlay -> {
                    resumeMedia()
                }
                handlerPause -> {
                    pauseMedia()
                }
                handlerSkipPrevious -> {
                    skipPreviousMedia()
                }
                handlerSkipNext -> {
                    skipNextMedia()
                }
                handlerCancel -> {
                    cancelMedia()
                }
            }
        }

        createNotification(applicationContext, "song " + curentPlaying, "unknown")

        return START_STICKY
    }

    public fun createNotification(context: Context, songName: String, singerName: String) {
        val customNotification =  RemoteViews(context.packageName, R.layout.notification_layout)
        customNotification.setTextViewText(R.id.txt_song_name, songName)
        customNotification.setTextViewText(R.id.txt_singer_name, singerName)
        if(isPlaying == true) {
            customNotification.setOnClickPendingIntent(R.id.btn_play, handlerCick(handlerPause))
            customNotification.setImageViewResource(R.id.btn_play, R.drawable.pause_icon)
        } else {
            customNotification.setOnClickPendingIntent(R.id.btn_play, handlerCick(handlerPlay))
            customNotification.setImageViewResource(R.id.btn_play, R.drawable.play_icon)
        }
        customNotification.setOnClickPendingIntent(R.id.btn_skip_next, handlerCick(handlerSkipNext))
        customNotification.setOnClickPendingIntent(R.id.btn_skip_previous, handlerCick(handlerSkipPrevious))
        customNotification.setOnClickPendingIntent(R.id.btn_cancel, handlerCick(handlerCancel))


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.play_icon)
            .setCustomContentView(customNotification)
            .setSound(null)
            .build()

        startForeground(1, notification)
    }

    private fun handlerCick(handler: Int): PendingIntent? {
        var intent = Intent(this, ServiceReceiver::class.java)
        intent.putExtra("handle", handler)
        Log.d(TAG, "handle: " + handler)
        return PendingIntent.getBroadcast(this, handler, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    public fun pauseMedia() {
        Log.d(TAG, "pause")
        player.pause()
        createNotification(applicationContext, "song " + curentPlaying, "unknow")
        isPlaying = false
    }

    public fun resumeMedia() {
        Log.d(TAG, "resume")
        player.start()
        createNotification(applicationContext, "song " + curentPlaying, "unknow")
        isPlaying = true
    }

    public fun skipNextMedia() {
        Log.d(TAG, "skip_next")
        player.stop()
        player.release()
        player = MediaPlayer()
        curentPlaying++
        if(curentPlaying >= listSong.size) curentPlaying = 0
        val resourceId = listSong[curentPlaying]
        val fileDescriptor = resources.openRawResourceFd(resourceId)
        if (fileDescriptor != null) {
            player.setDataSource(fileDescriptor.fileDescriptor, fileDescriptor.startOffset, fileDescriptor.length)
            fileDescriptor.close()
            player.prepare()
            player.start()
        }
        createNotification(applicationContext, "song " + curentPlaying, "unknow")
    }

    public fun skipPreviousMedia() {
        Log.d(TAG, "skip_previous")
        player.stop()
        player.release()
        player = MediaPlayer()
        curentPlaying--
        if(curentPlaying < 0) curentPlaying = listSong.size - 1
        val resourceId = listSong[curentPlaying]
        val fileDescriptor = resources.openRawResourceFd(resourceId)
        if (fileDescriptor != null) {
            player.setDataSource(fileDescriptor.fileDescriptor, fileDescriptor.startOffset, fileDescriptor.length)
            fileDescriptor.close()
            player.prepare()
            player.start()
        }
        createNotification(applicationContext, "song " + curentPlaying, "unknow")
    }

    public fun cancelMedia() {
        Log.d(TAG, "stop")
        stopSelf()
    }
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
        Log.d(TAG, "Service is stop")
    }

}