package com.gimbernat.radarriders.models


data class SearchHistory(
        var location: Location? = null,
        var user: Users? = null,
        var id: String? = null
){
    constructor() : this(null, null, null)
}
