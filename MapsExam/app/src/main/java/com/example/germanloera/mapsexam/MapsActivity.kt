package com.example.germanloera.mapsexam

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ListView
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.security.Permission
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener  {


private val PERMISSION_REQUEST_CODE =101;



    override fun onMyLocationClick(p0: Location) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        requestPermission();
        var mapHandlers =  MapEventHandlers(this, mMap);
        mMap.setOnCameraIdleListener(mapHandlers)
        mMap.setOnCameraMoveCanceledListener(mapHandlers);
        mMap.setOnCameraMoveListener(mapHandlers);
        mMap.setOnCameraMoveStartedListener(mapHandlers);
        mMap.setOnInfoWindowClickListener(mapHandlers)




        // Add a marker in Sydney and move the camera

    }

    private fun requestPermission(){

        val hasPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(hasPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                this.PERMISSION_REQUEST_CODE)
        }else{
            setUpMaps();
        }


    }

    @SuppressLint("MissingPermission")
    private fun setUpMaps(){

            mMap.isMyLocationEnabled = true;

        mMap.uiSettings.isZoomControlsEnabled = true;
        mMap.uiSettings.isMyLocationButtonEnabled =  true;

        val cdmx = LatLng(19.4325987, -99.1419372)
        mMap.run {
            //addMarker(MarkerOptions().position(cdmx).title("CDMX"))
            moveCamera(CameraUpdateFactory.newLatLngZoom(cdmx, 15f))


        }


        mMap.setInfoWindowAdapter(InfoWindow(this))

}

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            this.PERMISSION_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "Es necesario el permiso de ubicación", Toast.LENGTH_LONG).show()
                    requestPermission()

                }else if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    setUpMaps()

                }



            }




        }


    }



    fun setupResults(places:nearbySearch){
        var listview = this.findViewById<RecyclerView>(R.id.placesList)
        this.btn_ocultar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

               if( listview.visibility == View.VISIBLE){
                   btn_ocultar.text = "Mostrar"
                   listview.visibility = View.GONE
               }else{
                   btn_ocultar.text = "Ocultar"
                   listview.visibility = View.VISIBLE
               }


            }


        })





        var adapter = PlacesAdapter(this, places)
        listview.layoutManager = LinearLayoutManager(this)
        listview.adapter = adapter






    }






}
