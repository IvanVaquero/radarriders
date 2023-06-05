package com.gimbernat.radarriders.datasources.interfaces

import com.gimbernat.radarriders.models.Radar

interface IRadarDataSource {

    suspend fun fetch(): List<Radar>
    fun getRadar(id: String): Radar?
    fun createRadar(radar: Radar): Boolean
    fun editRadar(radar: Radar): Boolean
    fun deleteRadar(id: String): Boolean

}