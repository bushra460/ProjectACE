package com.cbsa.mcoe.ace.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cbsa.mcoe.ace.R
import com.cbsa.mcoe.ace.activities.ViewHotspotDetails
import com.cbsa.mcoe.ace.data_classes.NewDataClassHotspot
import com.cbsa.mcoe.ace.activities.hotspotArrayList
import com.cbsa.mcoe.ace.activities.selectedCar
import com.squareup.picasso.Picasso

class ListViewAdapter(val context: Context, val hotspotList: ArrayList<NewDataClassHotspot>): RecyclerView.Adapter<ListViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = hotspotList[position].hotspotDesc
        holder.txtSubtitle.text = hotspotList[position].hotspotDetails[0].notes
        Picasso.get().load(hotspotList[position].hotspotDetails[0].uri).into(holder.thumbnail)
        holder.itemView.id = hotspotList[position].hotspotId!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        v.setOnClickListener {
            val intent = Intent(context, ViewHotspotDetails::class.java)
            intent.putExtra("hotspotID", v.id)
            context.startActivity(intent)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        var index = 0
        hotspotArrayList.forEach {
            if (it.carId == selectedCar.carId){
                index++
            }
        }
        return index
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtSubtitle: TextView = itemView.findViewById(R.id.txtSubtitle)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    }

}