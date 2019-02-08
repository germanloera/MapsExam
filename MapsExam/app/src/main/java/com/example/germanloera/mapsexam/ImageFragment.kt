package com.example.germanloera.mapsexam

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image.view.*
import kotlinx.android.synthetic.main.list_item.view.*


class ImageFragment : Fragment() {
    // TODO: Rename and change types of parameters


    var url = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       url = this.arguments["url"] as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var  view = inflater.inflate(R.layout.fragment_image, container, false)

        Picasso.get().load(url).into(view.photo)
        return view;
    }



}
