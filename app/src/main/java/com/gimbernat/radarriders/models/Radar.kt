package com.gimbernat.radarriders.models

data class Radar(
    var radarName: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var limit: Int = 0,
    var id: String? = null,
    var user: String? = ""
) {
    constructor() : this("", 0.0,0.0,0, null, null)
}

