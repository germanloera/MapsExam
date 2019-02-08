package com.example.germanloera.mapsexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.info_window_layout.view.*

class InfoWindow(val context:Context) : GoogleMap.InfoWindowAdapter {



    override fun getInfoContents(p0: Marker?): View {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        var view = LayoutInflater.from(context).inflate(R.layout.info_window_layout, null)
        var place = p0?.tag as result

        view.card_view_image_title.text  = place.name





    Picasso.get().load(place.icon).into(view.card_view_image_marker_icon)

        return view

    }

    override fun getInfoWindow(p0: Marker?): View? {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    return null
    }
}