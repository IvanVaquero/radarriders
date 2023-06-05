package com.gimbernat.radarriders.datasources.interfaces

import com.gimbernat.radarriders.models.Alert

interface IAlertDataSource {

    suspend fun fetch(): List<Alert>
    fun getAlert(id: String): Alert?
    fun createAlert(alert: Alert): Boolean
    fun editAlert(alert: Alert): Boolean
    fun deleteAlert(id: String): Boolean

}