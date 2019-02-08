package com.example.germanloera.mapsexam

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService{

    @GET("nearbysearch/json")
    fun LoadNearbyPlaces(@Query("location") location:String,
                        @Query("radius") radius:String,
                         @Query("types") types:String,
                         @Query("key") key:String): Observable<nearbySearch>

    @GET("details/json")
    fun LoadPlaceDetails(@Query ("placeid") placeid: String,
                         @Query("key") key: String) : Observable<place>




    companion object {
        fun create(): ApiService{

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/place/")

                .build()

            return retrofit.create(ApiService::class.java)

        }
    }


}