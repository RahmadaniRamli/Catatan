package com.lazday.kotlinroommvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lazday.kotlinroommvvm.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
    }

    fun setupListener() {
        button_create.setOnClickListener{
            startActivity(Intent(this, EditActivity::class.java))
        }
    }

}