package com.uti.lumbung

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)cd
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val username = etUsername.getText().toString()
            val password = etPassword.getText().toString()
            if (validateLogin(username, password)) {
                // Login berhasil, navigasi ke halaman berikutnya
                val intent = Intent(
                    this@LoginActivity,
                    HomeActivity::class.java
                )
                startActivity(intent)
                finish() // Selesai LoginActivity agar tidak bisa kembali dengan tombol back
            } else {
                // Login gagal, tampilkan pesan kesalahan
                Toast.makeText(
                    this@LoginActivity,
                    "Login failed. Please check your credentials.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        // Misalnya, kita melakukan validasi sederhana di sini.
        // Anda dapat mengganti logika validasi sesuai dengan kebutuhan aplikasi Anda.
        return username == "admin" && password == "admin123"
    }
}
