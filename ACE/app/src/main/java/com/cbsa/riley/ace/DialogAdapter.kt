package com.cbsa.riley.ace

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class DialogAdapter( val makeArray: ArrayList<String>): RecyclerView.Adapter<DialogAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = makeArray[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        v.setOnClickListener {
            println(v.textTitle.text)
//            selectedCar.make = v.textTitle.text.toString()
            
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return makeArray.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.textTitle)
    }
}