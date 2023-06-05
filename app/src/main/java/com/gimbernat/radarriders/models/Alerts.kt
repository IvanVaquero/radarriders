package com.gimbernat.radarriders.models

data class Alerts(
        var desc: String = "",
        var title: String = "",
        var user: Users? = null,
        var radar: Radars? = null,
        var comment: List<Comments> = listOf(),
        var id: String? = null
){
   constructor() : this("","", null, null, listOf(), null)
}

