package com.cbsa.riley.ace

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class SearchAdapter(val hotspotList: ArrayList<NewDataClassHotspot>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = hotspotList[position].hotspotDesc
        holder.txtSubtitle.text = hotspotList[position].notes
        Picasso.get().load(hotspotList[position].hotspotUri).into(holder.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return hotspotArrayList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtSubtitle: TextView = itemView.findViewById(R.id.txtSubtitle)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    }

}