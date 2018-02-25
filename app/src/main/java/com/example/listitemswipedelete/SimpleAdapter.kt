package com.example.listitemswipedelete

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_item.view.*
import android.view.View
import android.widget.TextView

class SimpleAdapter(private val items: MutableList<String>) : RecyclerView.Adapter<SimpleAdapter.VH>() {

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.row_item.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return VH(view)
    }

    fun addItem(name: String) {
        items.add(name)
        notifyItemInserted(items.size)
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var row_item: TextView = itemView.row_item
        init {
        }
    }
}
