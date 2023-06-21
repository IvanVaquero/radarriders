package com.gimbernat.radarriders.datasources.interfaces

import com.gimbernat.radarriders.models.Radar

interface IRadarDataSource {

    suspend fun fetch(): List<Radar>

//  Se obtiene el Radar
    fun getRadar(id: String): Radar?

//  Se crea el Radar
    fun createRadar(radar: Radar): Boolean

//  Se edita el Radar TO-IMPROVE
    fun editRadar(uid: String, idRadar: String, radar: Radar): Boolean

//  Se elimina el Radar TO-IMPROVE
    fun deleteRadar(uid: String, idRadar: String): Boolean

}