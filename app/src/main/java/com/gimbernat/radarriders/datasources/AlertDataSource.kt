package com.gimbernat.radarriders.datasources
import android.util.Log
import com.gimbernat.radarriders.datasources.interfaces.IAlertDataSource
import com.gimbernat.radarriders.models.Alert
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * A data source for Firebase Authentication. This class provides methods for logging in,
 * signing up, and logging out users, as well as checking the current authentication state.
 */
class AlertDataSource(private val database: FirebaseDatabase) : IAlertDataSource {
    private var alerts: List<Alert> = mutableListOf<Alert>()

    fun getAll(callback: (List<Alert>) -> Unit)  {
        val ref = database.getReference("Alerts")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedAlerts = mutableListOf<Alert>()
                for (alertSnapshot in snapshot.children) {
                    val alert = alertSnapshot.getValue(Alert::class.java)
                    if (alert != null) {
                        alert.id = alertSnapshot.key
                        fetchedAlerts.add(alert)
                    }
                }
                alerts = fetchedAlerts
                callback(fetchedAlerts)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    override suspend fun fetch(): List<Alert> {
        return suspendCoroutine { continuation ->
            val ref = database.getReference("Alerts")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedAlerts = mutableListOf<Alert>()
                    for (alertSnapshot in snapshot.children) {
                        val alert = alertSnapshot.getValue(Alert::class.java)
                        if (alert != null) {
                            alert.id = alertSnapshot.key
                            fetchedAlerts.add(alert)
                        }
                    }
                    //Updating local copy
                    alerts = fetchedAlerts
                    continuation.resume(fetchedAlerts)
                }
                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override fun getAlert(id: String): Alert? {
        return alerts.find { it.id == id }
    }

    override fun createAlert(alert: Alert): Boolean {
        return try {
            val uid = UUID.randomUUID().toString()
            val newId = UUID.randomUUID().toString()
            val alertsTable = database.getReference("Alerts").child(uid).child(newId)
            alertsTable.setValue(alert)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun editAlert(uid: String, idAlert: String, alert: Alert): Boolean {
        return try {
            val alertsTable = database.getReference("Alerts").child(uid)
            alertsTable.setValue(alert)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun deleteAlert(uid: String, idAlert: String): Boolean {
        return try {
            val alertsTable = database.getReference("Alerts").child(uid).child(idAlert)
            alertsTable.removeValue()
            true
        } catch (e: Exception) {
            false
        }
    }
}