package com.gimbernat.radarriders.datasources.interfaces

import com.gimbernat.radarriders.models.Alert

interface IAlertDataSource {

    suspend fun fetch(): List<Alert>

//  Se obtiene la Alerta
    fun getAlert(id: String): Alert?

//  Se crea la Alerta
    fun createAlert(alert: Alert): Boolean

//  Se edita la Alerta TO-IMPROVE
    fun editAlert(uid: String, idAlert: String, alert: Alert): Boolean

//  Se elimina la Alerta TO-IMPROVE
    fun deleteAlert(uid: String, idAlert: String): Boolean

}