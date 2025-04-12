package com.example.exp7_100

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val loadButton = findViewById<Button>(R.id.loadButton)

        saveButton.setOnClickListener {
            val regNo = findViewById<EditText>(R.id.registerNumberEditText).text.toString()
            val name = findViewById<EditText>(R.id.nameEditText).text.toString()
            val cgpa = findViewById<EditText>(R.id.cgpaEditText).text.toString()

            if (regNo.isNotEmpty() && name.isNotEmpty() && cgpa.isNotEmpty()) {
                writeFileOnExternalStorage(regNo, name, cgpa)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        loadButton.setOnClickListener {
            val file = File(getExternalFilesDir(null), "StudentData/StudentDetails.txt")
            if (file.exists()) {
                val content = file.readText()
                Toast.makeText(this, content, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun writeFileOnExternalStorage(regNo: String, name: String, cgpa: String) {
        val folder = File(getExternalFilesDir(null), "StudentData")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = File(folder, "StudentDetails.txt")
        FileOutputStream(file, true).bufferedWriter().use { writer ->
            writer.appendLine("Register Number: $regNo, Name: $name, CGPA: $cgpa")
        }

        Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
    }
}
