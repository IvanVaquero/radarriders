package com.gimbernat.radarriders.models

data class Radar(
    var RadarName: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var Limit: Int = 0,
    var id: String? = null,
    var user: Users? = null
) {
    constructor() : this("", 0.0,0.0,0, null, null)
}

