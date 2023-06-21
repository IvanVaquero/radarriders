package com.gimbernat.radarriders.datasources
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
    private var radars: List<Radar> = mutableListOf<Radar>()

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
                TODO("Not yet implemented")
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
                    //Updating local copy
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
            val radarsTable = database.getReference("RadarRiders").child("Radars").child(newId)
            radarsTable.setValue(radar)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun editRadar(uid: String, idRadar: String, radar: Radar): Boolean {
        return try {
            val radarsTable = database.getReference("RadarRiders").child("Radars").child(uid).child(idRadar)
            radarsTable.setValue(radar)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun deleteRadar(uid: String, idRadar: String): Boolean {
        return try {
            val radarsTable = database.getReference("RadarRiders").child("Radars").child(uid).child(idRadar)
            radarsTable.removeValue()
            true
        } catch (e: Exception) {
            false
        }
    }
}