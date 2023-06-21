package com.gimbernat.radarriders.models

data class Alert(
        var Desc: String = "",
        var Title: String = "",
        var user: String? = "",
        var radar: Radar? = null,
        var comment: String? = "",
        var id: String? = null
){
   constructor() : this("","", null, null, "", null)
}

