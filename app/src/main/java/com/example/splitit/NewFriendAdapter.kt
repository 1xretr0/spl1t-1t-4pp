package com.example.splitit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class NewFriendAdapter: RecyclerView.Adapter<NewFriendAdapter.ViewHolder>() {
    private var dataset: ArrayList<Database.FriendRecord> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder
    {
       val view = LayoutInflater.from(parent.context).inflate(
           R.layout.item_new_friend, parent, false
       )
       return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val f = dataset[position]

        holder.name.text = f.name
        holder.splitAmount.text = String.format("%.2f", f.splitAmount)
        holder.seekBar.max = f.total?.roundToInt() ?: String.format("%.0f", f.splitAmount).toInt()
        holder.seekBar.progress = f.splitAmount?.roundToInt() ?: String.format("%.0f", f.splitAmount).toInt()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun addFriendData(friendRecords: ArrayList<Database.FriendRecord>) {
        dataset.addAll(friendRecords)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView
        val splitAmount: TextView
        val seekBar: SeekBar
        init {
            super.itemView
            name = itemView.findViewById(R.id.new_split_friendname_txt)
            splitAmount = itemView.findViewById(R.id.new_split_friendamount_txt)
            seekBar = itemView.findViewById(R.id.new_split_amount_skb)
        }
    }
}