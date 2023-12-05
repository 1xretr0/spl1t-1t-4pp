package com.example.splitit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun addFriendData(friendRecords: List<Database.FriendRecord>) {
        dataset.addAll(friendRecords)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView
        init {
            super.itemView
            name = itemView.findViewById(R.id.new_split_friendname_txt)
        }
    }
}