package com.gimbernat.radarriders.models


data class SearchHistory(
        var lat: Int = 0,
        var lng: Int = 0
){
    constructor() : this(0,0)
}
