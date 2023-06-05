package com.gimbernat.radarriders.models


data class SearchHistory(
        var lat: Int = 0,
        var lng: Int = 0,
        var user: Users? = null,
        var id: String? = null
){
    constructor() : this(0,0, null, null)
}
