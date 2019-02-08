package com.example.germanloera.mapsexam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class PlacesAdapter(private  val context: Context, private val places:nearbySearch) :  RecyclerView.Adapter<placeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): placeViewHolder {
        return placeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    return places.results.size
    }

    override fun onBindViewHolder(holder: placeViewHolder?, position: Int) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        if (holder != null) {
            holder.setup(places.results[position])
        }

    }


}