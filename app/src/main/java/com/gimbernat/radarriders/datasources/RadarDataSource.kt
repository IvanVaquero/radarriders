package com.gimbernat.radarriders.datasources
import com.gimbernat.radarriders.datasources.interfaces.IRadarDataSource
import com.gimbernat.radarriders.models.Radars
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * A data source for Firebase Authentication. This class provides methods for logging in,
 * signing up, and logging out users, as well as checking the current authentication state.
 */
class RadarDataSource(private val database: FirebaseDatabase) : IRadarDataSource {
    private var radars: List<Radars> = mutableListOf<Radars>()

    fun subscribe(callback: (List<Radars>) -> Unit)  {
        val ref = database.getReference("radars")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedRadarss = mutableListOf<Radars>()

                for (radarSnapshot in snapshot.children) {
                    val radar = radarSnapshot.getValue(Radars::class.java)
                    if (radar != null) {
                        radar.id = radarSnapshot.key
                        fetchedRadarss.add(radar)
                    }
                }

                //Updating local copy
                radars = fetchedRadarss
                callback(fetchedRadarss)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList()) // or callback with some default value
            }
        })
    }

    override suspend fun fetch(): List<Radars> {
        return suspendCoroutine { continuation ->

            val ref = database.getReference("radars")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedRadarss = mutableListOf<Radars>()

                    for (radarSnapshot in snapshot.children) {
                        val radar = radarSnapshot.getValue(Radars::class.java)
                        if (radar != null) {
                            radar.id = radarSnapshot.key
                            fetchedRadarss.add(radar)
                        }
                    }

                    //Updating local copy
                    radars = fetchedRadarss

                    continuation.resume(fetchedRadarss)
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override fun get(id: String): Radars? {
        return radars.find { it.id == id }
    }
}