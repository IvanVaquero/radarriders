package com.gimbernat.radarriders.models

data class Radar(
    var RadarName: String = "",
    var IniLocation: List<Location> = listOf(),
    var EndLocation: List<Location> = listOf(),
    var Limit: Int = 0,
    var Static: Boolean = false,
    var id: String? = null,
    var user: Users? = null
) {
    constructor() : this("", listOf(),listOf(),0, false, null, null)
}

