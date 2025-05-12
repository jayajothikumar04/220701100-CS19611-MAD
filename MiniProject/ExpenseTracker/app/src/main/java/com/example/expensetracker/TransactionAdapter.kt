package com.example.expensetracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.trans_date)
        val amount: TextView = view.findViewById(R.id.trans_amount)
        val category: TextView = view.findViewById(R.id.trans_category)
        val container: View = view.findViewById(R.id.transaction_row)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = transactions[position]
        holder.date.text = item.date
        holder.amount.text = (if (item.isIncome) "+" else "-") + "₹%.2f".format(item.amount)
        holder.category.text = item.category

        if (item.isIncome) {
            holder.container.setBackgroundColor(Color.parseColor("#C8E6C9")) // light green
        } else {
            holder.container.setBackgroundColor(Color.parseColor("#FFCDD2")) // light red
        }
    }

    override fun getItemCount() = transactions.size
}
