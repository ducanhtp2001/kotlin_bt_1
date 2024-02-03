package com.example.kotlin_1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity(){

    private lateinit var btnBackToMainActivity : Button
    private lateinit var btnNextToThirdActivity : Button
    private lateinit var tvSecondTotalAge : TextView

    override fun onCreate(bundle : Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.second_activity_layout)
        Log.d("a", "on create")

        var preIntent = intent
        var totalAge : Int? = preIntent.getIntExtra("totalAge", -1).let {
            if (it == -1) Toast.makeText(applicationContext, "no value", Toast.LENGTH_LONG).show()
            it
        }
        tvSecondTotalAge = findViewById(R.id.tvSecondTotalAge)
        tvSecondTotalAge.text = totalAge.toString()
        btnBackToMainActivity = findViewById(R.id.btnBackToMainActivity)
        btnNextToThirdActivity = findViewById(R.id.btnNextToThirdActivity)
        btnBackToMainActivity.setOnClickListener() {
            var intent : Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        btnNextToThirdActivity.setOnClickListener {
            var intent = Intent(applicationContext, ThirdActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("a", "on destroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("a", "on pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("a", "on stop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("a", "on restart")

    }
}