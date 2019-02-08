package com.example.germanloera.mapsexam

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_place_details.*

class PlaceDetails : AppCompatActivity() {


    val apiService by lazy {
        ApiService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)

        var result = intent.getSerializableExtra("result") as result


        var placesSaved =  getSharedPreferences("placesCache", Context.MODE_PRIVATE )
       if( placesSaved.contains(result.place_id) ){



           var gson =  Gson()
           var placeStr = placesSaved.getString(result.place_id, "");
           var place = gson.fromJson<place>(placeStr, place::class.java)
           setupDetails(place)


       }else {


           var placeResult =
               apiService.LoadPlaceDetails(result.place_id, getString(R.string.google_maps_key)).subscribeOn(
                   Schedulers.io()
               )
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe({ placeDetails ->
                       setupDetails(placeDetails)
                   },
                       { error ->
                           error.printStackTrace()
                           Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                       })


       }

    }




    fun setupDetails(placeResult: place){
        var place = placeResult;

        if(placeResult.status.equals("OVER_QUERY_LIMIT")) {
            val jsonfile: String = this.assets.open("place.json").bufferedReader().use { it.readText() }
            var gson = Gson();
            place = gson.fromJson<place>(jsonfile, com.example.germanloera.mapsexam.place::class.java);
            Toast.makeText(this, "Contenido cargado desde el archivo local", Toast.LENGTH_LONG).show()

        }
        var result = intent.getSerializableExtra("result") as result

        var gson = Gson()
        var  str = gson.toJson(place);
        var placesSaved =  getSharedPreferences("placesCache", Context.MODE_PRIVATE )

        var editor = placesSaved.edit()
        editor.putString(result.place_id, str)
        editor.commit()


        this.tv_name.text = place.result.name
        this.tv_rating.text = place.result.rating
        this.tv_address.text =place.result.vicinity
        this.tv_website.text = place.result.website



if(place.result.photos != null) {
    this.pager.adapter = ViewPagerAdapter(this, place.result.photos, this.supportFragmentManager)
}else{
    Toast.makeText(this, "Sin fotos disponibles", Toast.LENGTH_LONG).show()
}


        this.ib_share.setOnClickListener {
            view: View? ->

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, place.result.url)
                type = "text/plain"
            }
            startActivity(sendIntent)

        }


    }








}
