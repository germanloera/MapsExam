package com.example.germanloera.mapsexam

import java.io.Serializable


data class nearbySearch(var html_attributions: List<String>,
                        var next_page_token:String,
                        var results: List<result>,
                        var status:String,
                        var error_message:String) : Serializable

data class result(var geometry:Geometry,
                  var icon:String,
                  var id:String,
                  var name:String,
                  var photos:List<photo>,
                  var place_id:String,
                  var scope:String,
                  var types:List<String>,
                  var vicinity:String)  : Serializable

data class photo(var height:Int,
                 var html_attributions: List<String>,
                 var photo_reference:String,
                 var width:Int)  : Serializable

data class Geometry(var location: Location,
                    var viewport:Viewport) : Serializable

data class Location(var lat:String,
                    var lng:String)  : Serializable

data class Viewport(var northeast:Location,
                    var southwest:Location)  : Serializable



data class place(var html_attributions: List<String>,
                 var result: placeResult,
                 var status: String)


data class placeResult(
                       var adr_address:String,
                       var formatted_address:String,
                       var formatted_phone_number:String,
                       var geometry:Geometry,
                       var icon:String,
                       var id:String,
                       var rating:String,
                       var international_phone_number:String,
                       var name:String,
                       var photos: List<photo>,
                       var vicinity: String,
                       var url:String,
                       var website:String,
                       var status: String,
var reviews:List<review>
                       )


data class review ( var author_name:String,
                   var author_url:String,
                   var language:String,
                    var photos:List<photo>,
                   var profile_photo_url:String,
                   var rating:String,
                   var relative_time_description:String,
                   var text:String,
                   var time:String)