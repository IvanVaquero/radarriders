package com.gimbernat.radarriders.models

data class Users(
        var Email: String = "",
        var Foto: String = "",
        var Password: String = "",
        var UserName: String = "",
        var isAdmin: Boolean = false,
        var id: String? = null
){
    constructor() : this("","","","", false, null)
}

