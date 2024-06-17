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

    private fun loginDatabase(username: String, password: String) {
//        periksa apakah username dan password sudah diisi
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username atau Password tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val userExists = databaseHelper.readUser(username, password)
        if (userExists) {
//            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Username atau Password tidak sesuai !", Toast.LENGTH_SHORT).show()
        }
    }
}
