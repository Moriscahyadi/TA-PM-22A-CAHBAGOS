package com.uti.lumbung

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uti.lumbung.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var databaseHelper: dbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = dbHelper(this)
    }
}
