package com.example.expensetracker

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var incomeText: TextView
    private lateinit var expenseText: TextView
    private lateinit var balanceText: TextView
    private lateinit var transactionRecycler: RecyclerView
    private lateinit var lineChart: LineChart
    private lateinit var adapter: TransactionAdapter
    private val transactions = mutableListOf<Transaction>()
    private val PREFS_NAME = "expense_prefs"
    private val TRANSACTION_KEY = "transactions"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        incomeText = findViewById(R.id.total_income)
        expenseText = findViewById(R.id.total_expense)
        balanceText = findViewById(R.id.total_balance)
        transactionRecycler = findViewById(R.id.transaction_recycler)
        lineChart = findViewById(R.id.lineChart)

        adapter = TransactionAdapter(transactions)
        transactionRecycler.layoutManager = LinearLayoutManager(this)
        transactionRecycler.adapter = adapter

        findViewById<Button>(R.id.add_income_btn).setOnClickListener {
            showAddDialog(true)
        }

        findViewById<Button>(R.id.add_expense_btn).setOnClickListener {
            showAddDialog(false)
        }

        loadTransactions()
        updateSummaries()
    }

    private fun showAddDialog(isIncome: Boolean) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_transaction, null)
        val alertDialog = AlertDialog.Builder(this).setView(dialogView).create()

        val amountInput = dialogView.findViewById<EditText>(R.id.input_amount)
        val categoryInput = dialogView.findViewById<EditText>(R.id.input_category)
        val addButton = dialogView.findViewById<Button>(R.id.add_transaction_btn)

        addButton.setOnClickListener {
            val amount = amountInput.text.toString().toDoubleOrNull()
            val category = categoryInput.text.toString()
            if (amount != null && category.isNotEmpty()) {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val transaction = Transaction(date, amount, category, isIncome)
                transactions.add(0, transaction)
                adapter.notifyItemInserted(0)
                saveTransactions()
                updateSummaries()
                alertDialog.dismiss()
            } else {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog.show()
    }

    private fun updateSummaries() {
        val income = transactions.filter { it.isIncome }.sumOf { it.amount }
        val expense = transactions.filter { !it.isIncome }.sumOf { it.amount }
        incomeText.text = "₹%.2f".format(income)
        expenseText.text = "₹%.2f".format(expense)
        balanceText.text = "₹%.2f".format(income - expense)

        updateGraph()
    }

    private fun updateGraph() {
        if (transactions.isEmpty()) {
            lineChart.clear()
            return
        }

        // Group by date and calculate totals
        val grouped = transactions.groupBy { it.date }.toSortedMap()
        val dates = grouped.keys.toList()

        val incomeEntries = mutableListOf<Entry>()
        val expenseEntries = mutableListOf<Entry>()
        val balanceEntries = mutableListOf<Entry>()

        var runningBalance = 0f

        for ((index, date) in dates.withIndex()) {
            val daily = grouped[date] ?: continue
            val incomeTotal = daily.filter { it.isIncome }.sumOf { it.amount }.toFloat()
            val expenseTotal = daily.filter { !it.isIncome }.sumOf { it.amount }.toFloat()
            runningBalance += (incomeTotal - expenseTotal)

            incomeEntries.add(Entry(index.toFloat(), incomeTotal))
            expenseEntries.add(Entry(index.toFloat(), expenseTotal))
            balanceEntries.add(Entry(index.toFloat(), runningBalance))
        }

        val incomeSet = LineDataSet(incomeEntries, "Income").apply {
            color = Color.GREEN
            setDrawCircles(false)
            setDrawValues(false)
        }

        val expenseSet = LineDataSet(expenseEntries, "Expenses").apply {
            color = Color.RED
            setDrawCircles(false)
            setDrawValues(false)
        }

        val balanceSet = LineDataSet(balanceEntries, "Balance").apply {
            color = Color.BLUE
            setDrawCircles(false)
            setDrawValues(false)
        }

        val data = LineData(incomeSet, expenseSet, balanceSet)
        lineChart.data = data

        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(dates)
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.axisRight.isEnabled = false
        lineChart.description.isEnabled = false

        lineChart.invalidate()
    }

    private fun saveTransactions() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(transactions)
        prefs.edit().putString(TRANSACTION_KEY, json).apply()
    }

    private fun loadTransactions() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(TRANSACTION_KEY, null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<Transaction>>() {}.type
            val savedList = Gson().fromJson<MutableList<Transaction>>(json, type)
            transactions.addAll(savedList)
            adapter.notifyDataSetChanged()
        }
    }
}
