package com.gimbernat.radarriders.datasources
import com.gimbernat.radarriders.datasources.interfaces.IRadarDataSource
import com.gimbernat.radarriders.models.Radar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * A data source for Firebase Authentication. This class provides methods for logging in,
 * signing up, and logging out users, as well as checking the current authentication state.
 */
class RadarDataSource(private val database: FirebaseDatabase) : IRadarDataSource {
    private var radars: List<Radar> = mutableListOf<Radar>()

    fun subscribe(callback: (List<Radar>) -> Unit)  {
        val ref = database.getReference("Radars")
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
                //Updating local copy
                radars = fetchedRadars
                callback(fetchedRadars)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList()) // or callback with some default value
            }
        })
    }

    override suspend fun fetch(): List<Radar> {
        return suspendCoroutine { continuation ->
            val ref = database.getReference("Radars")
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
//          Creamos aqui el objeto en la base de datos
            val radarsTable = database.getReference("Radars")
            radarsTable.setValue(radar)
            radarsTable.push()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun editRadar(radar: Radar): Boolean {
        return try {
//            Editamos aqui el objeto en la base de datos
            val radarsTable = database.getReference("Radars")
            radarsTable.setValue(radar)
            radarsTable.push()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun deleteRadar(id: String): Boolean {
        return try {
//          Eliminamos aqui el objeto en la base de datos
            val radarsTable = database.getReference("Radars")
            radarsTable.child(id).removeValue()
            true
        } catch (e: Exception) {
            false
        }
    }
}