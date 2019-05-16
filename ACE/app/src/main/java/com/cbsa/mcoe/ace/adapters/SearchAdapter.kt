package com.cbsa.mcoe.ace.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cbsa.mcoe.ace.activities.ImageViewPage
import com.cbsa.mcoe.ace.R
import com.cbsa.mcoe.ace.data_classes.NewDataClassCar
import com.cbsa.mcoe.ace.data_classes.NewDataClassCarImage
import com.squareup.picasso.Picasso

class SearchAdapter(val context: Context, val resultCar: ArrayList<NewDataClassCar>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val title = resultCar[position].make + " " + resultCar[position].model
        holder.txtTitle.text = title
        holder.txtSubtitle.text = resultCar[position].year
        holder.itemView.id = resultCar[position].carId

        val selectedCarImages = ArrayList<NewDataClassCarImage>()
        resultCar[position].imageArrayList?.forEach { image ->
            if (image.carId == resultCar[position].carId && image.exteriorImage) {
                selectedCarImages.add(image)
            }
            selectedCarImages.forEach {
                //Picasso.get().load(it.imageArrayList[position].carImageURI).into(holder.thumbnail)
                //uncomment when you have more images
                if (it.displayPic){
                    Picasso.get().load(it.carImageURI).into(holder.thumbnail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false)
        v.setOnClickListener {
            println("clicked car Id:   ${v.id}")
            val intent = Intent(context, ImageViewPage::class.java)
            intent.putExtra("carId", v.id)
            context.startActivity(intent)
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return resultCar.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtSubtitle: TextView = itemView.findViewById(R.id.txtSubtitle)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    }

}