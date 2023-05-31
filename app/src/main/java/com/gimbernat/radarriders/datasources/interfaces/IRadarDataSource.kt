package com.gimbernat.radarriders.datasources.interfaces

import com.gimbernat.radarriders.models.Radars

interface IRadarDataSource {

    suspend fun fetch(): List<Radars>

    /**
     * @param id String
     * @return Radars
     */
    fun get(id: String): Radars?
}