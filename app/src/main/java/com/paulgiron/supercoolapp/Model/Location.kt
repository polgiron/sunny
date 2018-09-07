package com.paulgiron.supercoolapp.Model

class Location(
    var consolidated_weather: ArrayList<Forecast>,
    var title: String,
    var location_type: String,
    var woeid: Int,
    var latt_long: String,
    var timezone: String
) { }