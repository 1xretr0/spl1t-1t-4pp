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
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_split, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = dataset[position]

        holder.storeName.text = s.storeName
        holder.total.text = s.total
        holder.status.text = if (s.status == "1") "Pagado" else "Pendiente"
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun addSplitsData(splitRecords: List<Database.SplitRecord>) {
        dataset.addAll(splitRecords)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeName: TextView
        val total: TextView
        val status: TextView
        init {
            super.itemView
            storeName = itemView.findViewById(R.id.home_split_txt2)
            total = itemView.findViewById(R.id.home_split_txt4)
            status = itemView.findViewById(R.id.home_split_txt3)
        }
    }
}