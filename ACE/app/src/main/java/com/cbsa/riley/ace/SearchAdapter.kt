package com.cbsa.riley.ace

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SearchAdapter(val carList: ArrayList<Car>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtMake?.text = carList[position].make
        holder?.txtModel?.text = carList[position].model
        holder?.txtYear?.text = carList[position].year
        holder?.txtTrim?.text = carList[position].trim
//        holder?.cardView?.id = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.search_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtMake = itemView.findViewById<TextView>(R.id.carMakeTVE)
        val txtModel = itemView.findViewById<TextView>(R.id.carModelTVE)
        val txtYear = itemView.findViewById<TextView>(R.id.carYearTVE)
        val txtTrim = itemView.findViewById<TextView>(R.id.carTrimTVE)
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
    }

//     fun setIntent(position:Int, contextE: Context, parent: ViewGroup){
//
////         var intent = Intent(parent?.context, MainActivity::class.java)
////         intent.putExtra("carMake", carList[position].make)
////         intent.putExtra("carModel", carList[position].model)
////         intent.putExtra("carYear", carList[position].year)
////         intent.putExtra("carTrim", carList[position].trim)
////
////         startActivity(intent)
//     }
}