package com.example.expensetracker

data class Transaction(
    val date: String,
    val amount: Double,
    val category: String,
    val isIncome: Boolean
)