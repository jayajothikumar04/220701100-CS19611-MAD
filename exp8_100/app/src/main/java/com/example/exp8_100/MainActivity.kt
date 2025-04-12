package com.example.exp8_100

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val showAlertButton = findViewById<Button>(R.id.showAlertButton)

        showAlertButton.setOnClickListener {
            val message = editText.text.toString()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("MAD Lab")
            builder.setMessage(message)
            builder.setCancelable(false)

            builder.setPositiveButton("OK") { dialog, _ ->
                Toast.makeText(this, "You clicked OK", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            builder.setNegativeButton("CANCEL") { dialog, _ ->
                Toast.makeText(this, "You clicked Cancel", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}
