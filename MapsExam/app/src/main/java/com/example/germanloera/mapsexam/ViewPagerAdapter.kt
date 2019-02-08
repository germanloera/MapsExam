package com.example.germanloera.mapsexam



import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class ViewPagerAdapter(var context: Context, var photos: List<photo>, var  fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
       return photos.size
    }

    override fun getItem(position: Int): android.support.v4.app.Fragment {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        var fragment = ImageFragment();
       var url =  "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference={ref}&key={key}"
        url = url.replace("{ref}", photos[position].photo_reference);
        var key = context.getString(R.string.google_maps_key);
        url = url.replace("{key}", key)
fragment.arguments = Bundle()
        fragment.arguments.putString("url", url)
        return fragment

    }




}