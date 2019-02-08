package com.example.germanloera.mapsexam

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_window_layout.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class placeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    fun setup(result: result){

        itemView.card_view_place_title.text = result.name;
        Picasso.get().load(result.icon).into(itemView.card_view_image_place)
itemView.setOnClickListener(object : View.OnClickListener{
    override fun onClick(p0: View?) {


        var intent = Intent(itemView.context, PlaceDetails::class.java)

        intent.putExtra("result", result)

        itemView.context.startActivity(intent)



    }


})

    }



}