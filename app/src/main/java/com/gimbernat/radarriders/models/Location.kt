package com.gimbernat.radarriders.models

data class Location(
        var latitude: Double = 0.0,
        var longitude: Double = 0.0,
){
        constructor() : this(0.0, 0.0)
}