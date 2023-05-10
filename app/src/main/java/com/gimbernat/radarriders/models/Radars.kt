package com.gimbernat.radarriders.models

data class Radars(
    var RadarName: String = "",
    var Limit: Int = 0,
    var Static: Boolean = false
) {
    constructor() : this("", 0, false)
}