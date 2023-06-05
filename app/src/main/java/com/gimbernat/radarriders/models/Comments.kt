package com.gimbernat.radarriders.models


data class Comments(
        var comment: String = "",
        var user: Users? = null,
        var id: String? = null
){
    constructor() : this("", null, null)
}
