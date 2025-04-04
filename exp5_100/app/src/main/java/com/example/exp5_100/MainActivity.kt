package com.example.exp5_100

import android.database.Cursor
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: UsersDBHelper
    private lateinit var etRegister: EditText
    private lateinit var etName: EditText
    private lateinit var etCGPA: EditText
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = UsersDBHelper(this)

        etRegister = findViewById(R.id.etRegister)
        etName = findViewById(R.id.etName)
        etCGPA = findViewById(R.id.etCGPA)
        tvResult = findViewById(R.id.tvResult)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val register = etRegister.text.toString()
            val name = etName.text.toString()
            val cgpa = etCGPA.text.toString().toDoubleOrNull()

            if (register.isNotEmpty() && name.isNotEmpty() && cgpa != null) {
                val success = dbHelper.insertStudent(UserModel(register, name, cgpa))
                tvResult.text = if (success) "Student Added" else "Error adding student"
            } else {
                tvResult.text = "Please fill all fields correctly!"
            }
        }

        findViewById<Button>(R.id.btnView).setOnClickListener {
            val cursor: Cursor = dbHelper.getAllStudents()
            if (cursor.count == 0) {
                tvResult.text = "No records found"
                return@setOnClickListener
            }
            val builder = StringBuilder()
            while (cursor.moveToNext()) {
                builder.append("Register: ${cursor.getString(0)}\n")
                builder.append("Name: ${cursor.getString(1)}\n")
                builder.append("CGPA: ${cursor.getDouble(2)}\n\n")
            }
            cursor.close()
            tvResult.text = builder.toString()
        }

        findViewById<Button>(R.id.btnModify).setOnClickListener {
            val register = etRegister.text.toString()
            val name = etName.text.toString()
            val cgpa = etCGPA.text.toString().toDoubleOrNull()

            if (register.isNotEmpty() && name.isNotEmpty() && cgpa != null) {
                val success = dbHelper.updateStudent(UserModel(register, name, cgpa))
                tvResult.text = if (success) "Student Updated" else "Update Failed"
            } else {
                tvResult.text = "Enter details correctly!"
            }
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val register = etRegister.text.toString()

            if (register.isNotEmpty()) {
                val success = dbHelper.deleteStudent(register)
                tvResult.text = if (success) "Student Deleted" else "Deletion Failed"
            } else {
                tvResult.text = "Enter Register Number!"
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            etRegister.text.clear()
            etName.text.clear()
            etCGPA.text.clear()
            tvResult.text = ""
        }
    }
}
