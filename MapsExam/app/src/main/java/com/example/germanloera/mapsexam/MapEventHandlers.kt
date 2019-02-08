package com.example.germanloera.mapsexam

import android.content.Context
import android.content.Intent

import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import android.location.Location.distanceBetween
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MapEventHandlers: GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveCanceledListener,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener{

    val apiService by lazy {
        ApiService.create()
    }
    var disposable: Disposable? = null



    private var mMap: GoogleMap
    private var context:Context

    constructor(context:Context, map: GoogleMap){
        mMap = map
        this.context = context

    }

    override fun onInfoWindowClick(p0: Marker?) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        var intent = Intent(context, PlaceDetails::class.java)
        var result = p0?.tag as result

        intent.putExtra("result", result)

        context.startActivity(intent)


    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


    if (p0 != null){
        p0.showInfoWindow()

    }
        return false
    }





    override fun onCameraIdle() {

       var visibleRegion = mMap.projection.visibleRegion


         var farRight = visibleRegion.farRight;
        var  farLeft = visibleRegion.farLeft;
        var nearRight = visibleRegion.nearRight;
        var  nearLeft = visibleRegion.nearLeft;

        var distanceWidth = floatArrayOf(0f, 0f)
                distanceBetween(
                        (farRight.latitude+nearRight.latitude)/2,
                        (farRight.longitude+nearRight.longitude)/2,
                        (farLeft.latitude+nearLeft.latitude)/2,
                        (farLeft.longitude+nearLeft.longitude)/2,
                        distanceWidth
                        );


                var distanceHeight  = floatArrayOf(0f, 0f)
                distanceBetween(
                        (farRight.latitude+nearRight.latitude)/2,
                        (farRight.longitude+nearRight.longitude)/2,
                        (farLeft.latitude+nearLeft.latitude)/2,
                        (farLeft.longitude+nearLeft.longitude)/2,
                        distanceHeight
                );

                var distance:Float;

                if (distanceWidth[0]>distanceHeight[0]){
                    distance = distanceWidth[0];
                } else {
                    distance = distanceHeight[0];
                }

        var location:String = mMap.cameraPosition.target.latitude.toString() + ","+mMap.cameraPosition.target.longitude.toString()

        this.loadPlacesNearby(location, distance.toInt().toString())

    }


    override fun onCameraMoveCanceled() {

    }

    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraMove() {

    }



    private fun loadPlacesNearby( location:String, radius:String){
        var key =  context.getString(R.string.google_maps_key);
        disposable = apiService.LoadNearbyPlaces(location, radius, "food", key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {result -> Process(result) },
                {error -> error.printStackTrace()
                    Toast.makeText(this.context, error.message, Toast.LENGTH_LONG).show() })





    }


    private fun Process(nearbySearch: nearbySearch){
var search = nearbySearch;

if(nearbySearch.status.equals("OVER_QUERY_LIMIT")) {
    val jsonfile: String = context.assets.open("response.json").bufferedReader().use { it.readText() }
var gson = Gson();
   search = gson.fromJson<nearbySearch>(jsonfile, com.example.germanloera.mapsexam.nearbySearch::class.java);
    Toast.makeText(this.context, "Contenido cargado desde el archivo local", Toast.LENGTH_LONG).show()

}

        var activity = context as MapsActivity

        activity.setupResults(search)

for( place in search.results) {

    val placeLocation = LatLng(place.geometry.location.lat.toDouble(), place.geometry.location.lng.toDouble())


    mMap.addMarker(MarkerOptions().position(placeLocation).title(place.name)).tag = place;

}




    }














}