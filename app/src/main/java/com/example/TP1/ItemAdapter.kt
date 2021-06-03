package com.example.TP1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val dataset: List<ItemToDo>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cb: CheckBox = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.cb.text = item.description
        holder.cb.isChecked = item.fait
        holder.view.setOnClickListener{
            itemClickListener.onItemClicked(holder.cb, position)
        }
    }

    override fun getItemCount() = dataset.size
}

interface OnItemClickListener {
    fun onItemClicked(v: View, pos: Int)
}