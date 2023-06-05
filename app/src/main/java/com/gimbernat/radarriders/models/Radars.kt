package com.gimbernat.radarriders.models

data class Radars(
    var RadarName: String = "",
    var IniLocation: List<String> = listOf(),
    var EndLocation: List<String> = listOf(),
    var Limit: Int = 0,
    var Static: Boolean = false,
    var id: String? = null,
    var user: Users? = null
) {
    constructor() : this("", listOf(),listOf(),0, false, null, null)
}

