package com.example.CatatanApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.CatatanApp.activity.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.editusername)
        password = findViewById(R.id.editpassword)
        button = findViewById(R.id.btnlogin)

        button.setOnClickListener {
            if (username.text.isNullOrBlank() && password.text.isNullOrBlank()){
                val toast = Toast.makeText(this, "Username dan Password Tidak Boleh Kosong", Toast.LENGTH_LONG)
                toast.show()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }
}