package com.example.exp6_100

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPin: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUsername)
        etPin = findViewById(R.id.etPin)
        btnLogin = findViewById(R.id.btnLogin)
        btnClear = findViewById(R.id.btnClear)

        btnLogin.setOnClickListener {
            validateInput()
        }

        btnClear.setOnClickListener {
            etUsername.text.clear()
            etPin.text.clear()
        }
    }

    private fun validateInput() {
        val username = etUsername.text.toString()
        val pin = etPin.text.toString()

        if (username.isEmpty() || pin.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val usernameRegex = Regex("^[a-zA-Z]+$")
        val pinRegex = Regex("^\\d{4}$")

        if (usernameRegex.matches(username) && pinRegex.matches(pin)) {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid User Name / Pin No.", Toast.LENGTH_SHORT).show()
        }
    }
}
