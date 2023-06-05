package com.gimbernat.radarriders.models

data class Alert(
        var desc: String = "",
        var title: String = "",
        var user: Users? = null,
        var radar: Radar? = null,
        var comment: List<Comment> = listOf(),
        var id: String? = null
){
   constructor() : this("","", null, null, listOf(), null)
}

