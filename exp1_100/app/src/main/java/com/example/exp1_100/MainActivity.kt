package com.example.exp1_100

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var buttonFont: Button
    private lateinit var buttonSize: Button
    private lateinit var buttonBg: Button
    private lateinit var mainLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize Views
        editText = findViewById(R.id.editTextText)
        buttonFont = findViewById(R.id.button4)
        buttonSize = findViewById(R.id.button5)
        buttonBg = findViewById(R.id.button6)
        mainLayout = findViewById(R.id.main)

        // Change Font on Click
        buttonFont.setOnClickListener {
            editText.typeface = Typeface.DEFAULT_BOLD
        }

        // Change Font Size on Click
        buttonSize.setOnClickListener {
            editText.textSize = 70f
        }

        // Change Background Color on Click
        buttonBg.setOnClickListener {
            mainLayout.setBackgroundColor(Color.RED)
        }
    }
}