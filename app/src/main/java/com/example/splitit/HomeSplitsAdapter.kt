package com.example.splitit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeSplitsAdapter: RecyclerView.Adapter<HomeSplitsAdapter.ViewHolder>() {
    private var dataset: ArrayList<Database.SplitRecord> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeSplitsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.home_split_template, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var s = dataset[position]
        holder.nombreTextView.text = s.storeName
    }

    override fun getItemCount(): Int {
        return 0
    }

    fun addSplitsData(splitRecords: List<Database.SplitRecord>) {
        dataset.addAll(splitRecords)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView
        init {
            nombreTextView = itemView.findViewById(R.id.home_split_txt2)
        }
    }
}