package com.gimbernat.radarriders.models


data class Comment(
        var comment: String = "",
        var user: Users? = null,
        var id: String? = null
){
    constructor() : this("", null, null)
}
