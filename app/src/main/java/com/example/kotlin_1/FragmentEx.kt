package com.example.kotlin_1

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.kotlin_1.Fragment.Fragment1
import com.example.kotlin_1.Fragment.Fragment2
import com.example.kotlin_1.databinding.FragmentExLayoutBinding

class FragmentEx : AppCompatActivity(){
    private lateinit var binding : FragmentExLayoutBinding
    private val fragment1 = Fragment1()
    private val fragment2 = Fragment2()
    private val fragment3 = Fragment1()
    private val fragment4 = Fragment2()
    private lateinit var fragmentSupport : FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_ex_layout)
        binding = FragmentExLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentSupport = supportFragmentManager.beginTransaction()

        listener()
    }

    private fun listener() {
        binding?.apply {
            btnReplayFragment1.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    if(fragment1.isDisplay) {
                        remove(fragment1)
                    } else {
                        replace(R.id.contentView1,fragment1)
                    }
                    commit()
                }
            }

            btnReplayFragment2.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    if(fragment2.isDisplay) {
                        remove(fragment2)
                    } else {
                        replace(R.id.contentView1,fragment2)
                    }
                    commit()
                }
            }

            btnReplayFragment3.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    if(fragment3.isDisplay) {
                        remove(fragment3)
                    } else {
                        replace(R.id.contentView2,fragment3)
                    }
                    commit()
                }
            }

            btnReplayFragment4.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    if(fragment4.isDisplay) {
                        remove(fragment4)
                    } else {
                        replace(R.id.contentView3,fragment4)
                    }
                    commit()
                }
            }
        }
    }
}