package com.example.exp2_100
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var inputText: EditText
    private lateinit var outputText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        outputText = findViewById(R.id.outputText)

        val buttons = listOf(
            R.id.b0, R.id.b1, R.id.b2, R.id.b3,
            R.id.b4, R.id.b5, R.id.b6, R.id.b7,
            R.id.b8, R.id.b9, R.id.bplus, R.id.bminus,
            R.id.bmul, R.id.bdiv, R.id.bdot
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { buttonClick(it) }
        }

        findViewById<Button>(R.id.bclr).setOnClickListener {
            inputText.setText("")
            outputText.text = ""
        }

        findViewById<Button>(R.id.beq).setOnClickListener {
            val result = evaluateExpression(inputText.text.toString())
            outputText.text = result
        }
    }

    private fun buttonClick(view: View) {
        val button = view as Button
        inputText.append(button.text)
    }

    private fun evaluateExpression(expression: String): String {
        return try {
            val tokens = expression.split("(?<=[-+/])|(?=[-+/])".toRegex()).map { it.trim() }
            if (tokens.isEmpty()) return "Error"

            var result = tokens[0].toDouble()

            var i = 1
            while (i < tokens.size) {
                val operator = tokens[i]
                val nextNumber = tokens[i + 1].toDouble()

                result = when (operator) {
                    "+" -> result + nextNumber
                    "-" -> result - nextNumber
                    "*" -> result * nextNumber
                    "/" -> if (nextNumber != 0.0) result / nextNumber else return "Error"
                    else -> return "Error"
                }
                i += 2
            }

            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }
}