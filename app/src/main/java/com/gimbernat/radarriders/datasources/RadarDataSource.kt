@file:Suppress("unused")

package com.gimbernat.radarriders.datasources
import android.util.Log
import com.gimbernat.radarriders.datasources.interfaces.IRadarDataSource
import com.gimbernat.radarriders.models.Radar
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
class RadarDataSource(private val database: FirebaseDatabase) : IRadarDataSource {
    private var radars: List<Radar> = mutableListOf()

//  Test para obtener mutableList de radars para markers mapa TO-IMPROVE
    fun getAllNOW(): List<Radar> {
        val ref = database.getReference("RadarRiders").child("Radars")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedRadars = mutableListOf<Radar>()
                for (radarSnapshot in snapshot.children) {
                    val radar = radarSnapshot.getValue(Radar::class.java)
                    if (radar != null) {
                        radar.id = radarSnapshot.key
                        fetchedRadars.add(radar)
                    }
                }
                radars = fetchedRadars
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return radars
    }

    fun getAll(callback: (List<Radar>) -> Unit)  {
        val ref = database.getReference("RadarRiders").child("Radars")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedRadars = mutableListOf<Radar>()
                for (radarSnapshot in snapshot.children) {
                    val radar = radarSnapshot.getValue(Radar::class.java)
                    if (radar != null) {
                        radar.id = radarSnapshot.key
                        fetchedRadars.add(radar)
                    }
                }
                radars = fetchedRadars
                callback(fetchedRadars)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    override suspend fun fetch(): List<Radar> {
        return suspendCoroutine { continuation ->
            val ref = database.getReference("RadarRiders").child("Radars")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedRadars = mutableListOf<Radar>()
                    for (radarSnapshot in snapshot.children) {
                        val radar = radarSnapshot.getValue(Radar::class.java)
                        if (radar != null) {
                            radar.id = radarSnapshot.key
                            fetchedRadars.add(radar)
                        }
                    }
                    radars = fetchedRadars
                    continuation.resume(fetchedRadars)
                }
                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override fun getRadar(id: String): Radar? {
        return radars.find { it.id == id }
    }

    override fun createRadar(radar: Radar): Boolean {
        return try {
            val newId = UUID.randomUUID().toString()
            val radarsTable =
                database.getReference("RadarRiders").child("Radars").child(newId)
            radarsTable.setValue(radar)
            Log.e("createRadar_true", "Radar Created")
            true
        } catch (e: Exception) {
            Log.e("createRadar_false", "Radar NOT Created")
            false
        }
    }

    override fun editRadar(uid: String, idRadar: String, radar: Radar): Boolean {
        return try {
            val radarsTable =
                database.getReference("RadarRiders").child("Radars").child(idRadar)
            radarsTable.setValue(radar)
            Log.e("editedRadar_true", "Radar Edited")
            true
        } catch (e: Exception) {
            Log.e("editedRadar_false", "Radar NOT Edited")
            false
        }
    }

    override fun deleteRadar(uid: String, idRadar: String): Boolean {
        return try {
            val radarsTable =
                database.getReference("RadarRiders").child("Radars").child(idRadar)
            radarsTable.removeValue()
            Log.e("deletedRadar_true", "Radar Deleted")
            true
        } catch (e: Exception) {
            Log.e("deletedRadar_false", "Radar NOT Deleted")
            false
        }
    }
}