package com.example.kotlin_1

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kotlin_1.Adapter.SinhVienAdapter
import com.example.kotlin_1.Model.SinhVien

class MainActivity : AppCompatActivity() {
    private var sinhVienLst : ArrayList<SinhVien> = ArrayList()
    private lateinit var sinhVienAdapter : SinhVienAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sinhVienLst.add(SinhVien("Nguyễn Văn A", 20))
        sinhVienLst.add(SinhVien("Nguyễn Văn B", 21))
        sinhVienLst.add(SinhVien("Nguyễn Văn C", 22))
        sinhVienLst.add(SinhVien("Nguyễn Văn D", 23))
        sinhVienLst.add(SinhVien("Nguyễn Văn E", 24))

        var tvTotalAge : TextView = findViewById(R.id.tvTotalAge)
        var btnCalculator : Button = findViewById(R.id.btnCalculator)
        var btnAdd : Button = findViewById(R.id.btnAddNewSV)
        var btnNextToSecondActivity : Button = findViewById(R.id.btnNextToSecondActivity)
        var lv : ListView = findViewById(R.id.lvMyListView)
        sinhVienAdapter = SinhVienAdapter(applicationContext, R.layout.sinh_vien_layout, sinhVienLst)
        lv.adapter = sinhVienAdapter
        btnCalculator.setOnClickListener{
            var totalAge : Int = 0
            for (item in sinhVienLst) {
                totalAge += item.age
            }
            tvTotalAge.text = "" + totalAge
        }

        btnAdd.setOnClickListener {
            showDialog()
        }

        btnNextToSecondActivity.setOnClickListener {
            var intent : Intent = Intent(applicationContext, SecondActivity::class.java)
            var totalAge : Int = Integer.parseInt(tvTotalAge.text?.toString().let {
                if (it == "")  -1
                else it

            }.toString())
            intent.putExtra("totalAge", totalAge)
            startActivity(intent)
        }
    }

    fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.input_dialog_layout)
        val name : EditText = dialog.findViewById(R.id.inputNameSV)
        val age : EditText = dialog.findViewById(R.id.inputAgeSV)
        val btnAdd : Button = dialog.findViewById(R.id.btnAddSV)
        val btnCancelDialog : Button = dialog.findViewById(R.id.btnCanceDialog)


        btnAdd.setOnClickListener {
            try {
                var sv = SinhVien(name.text.toString(), age.text.toString().toInt())
                if(sv != null) sinhVienLst.add(sv)
                sinhVienAdapter.notifyDataSetChanged()
            } catch (e: java.lang.Exception) {
                Toast.makeText(this, "Input your info", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        btnCancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }
}