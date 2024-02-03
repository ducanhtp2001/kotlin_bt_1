package com.example.kotlin_1

import android.content.*
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_1.BroadcastReceiver.MyBroadcastReceiver
import com.example.kotlin_1.Service.BackgroundService
import com.example.kotlin_1.Service.BoundService
import com.example.kotlin_1.Service.ForegroundService
import com.example.kotlin_1.databinding.ThirdActivityLayoutBinding

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding : ThirdActivityLayoutBinding
    private lateinit var boundService : BoundService
    private var isConnected = false
    private var myBroadcastReceiver = MyBroadcastReceiver()

    private var conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.MyBinder
            boundService = binder.getService()
            isConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnected = false
        }

    }

    override fun onCreate(saveState : Bundle?) {
        super.onCreate(saveState)
        setContentView(R.layout.third_activity_layout)
        binding = ThirdActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerReceiver(
            myBroadcastReceiver,
            IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        )


        binding.btnStartBoundService.setOnClickListener {
            Intent(this@ThirdActivity, BoundService::class.java).also {
                bindService(it, conn, Context.BIND_AUTO_CREATE)
            }
        }

        binding.btnStopBoundService.setOnClickListener {
            Log.d("BoundService", "isConnect = " + isConnected)
            if(isConnected) {
                unbindService(conn)
                isConnected = false
            } else Toast.makeText(this, "not connect", Toast.LENGTH_SHORT).show()
        }

        binding.btnInputNumber1.setOnClickListener {
            var number: Int =  binding.inputValue.text.toString().toInt() ?: 0
            binding.inputValue.setText("")
            if(isConnected) {
                boundService.number1 = number
            } else {
                Toast.makeText(this, "bind Service first", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnInputNumber2.setOnClickListener {
            var number: Int =  binding.inputValue.text.toString().toInt() ?: 0
            binding.inputValue.setText("")
            if(isConnected) {
                boundService.number2 = number
            } else {
                Toast.makeText(this, "bind Service first", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnOutputResult.setOnClickListener {
           if(isConnected) {
               binding.txtResult.text = "" + boundService.outputResult()
           } else {
               Toast.makeText(this, "bind Service first", Toast.LENGTH_SHORT).show()
           }
        }

        binding.btnStartForegroudService.setOnClickListener {
            var intent = Intent(this@ThirdActivity, ForegroundService::class.java)
            intent.putExtra("start", 1)
            startForegroundService(intent)
        }

        binding.btnStopForegroudService.setOnClickListener {
            var intent = Intent(this@ThirdActivity, ForegroundService::class.java)
            stopService(intent)
        }

        binding.btnBackToSecondActivity.setOnClickListener {
            var intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        binding.btnStartBackgroudService.setOnClickListener {
            startService(Intent(this@ThirdActivity, BackgroundService::class.java))
        }

        binding.btnStopBackgroudService.setOnClickListener {
            stopService(Intent(this@ThirdActivity, BackgroundService::class.java))
        }

        binding.btnNextToFragmentEX.setOnClickListener {
            Intent(this@ThirdActivity, FragmentEx::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadcastReceiver)
        if(isConnected) {
            unbindService(conn)
            isConnected = false
        } else Toast.makeText(this, "not connect", Toast.LENGTH_SHORT).show()
    }
}