package com.gimbernat.radarriders.datasources
import com.gimbernat.radarriders.datasources.interfaces.ICommentDataSource
import com.gimbernat.radarriders.models.Comment
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
class CommentDataSource(private val database: FirebaseDatabase) : ICommentDataSource {
    private var comments: List<Comment> = mutableListOf<Comment>()

    fun subscribe(callback: (List<Comment>) -> Unit)  {
        val ref = database.getReference("Comments")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedComments = mutableListOf<Comment>()
                for (commentSnapshot in snapshot.children) {
                    val comment = commentSnapshot.getValue(Comment::class.java)
                    if (comment != null) {
                        comment.id = commentSnapshot.key
                        fetchedComments.add(comment)
                    }
                }
                //Updating local copy
                comments = fetchedComments
                callback(fetchedComments)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList()) // or callback with some default value
            }
        })
    }

    override suspend fun fetch(): List<Comment> {
        return suspendCoroutine { continuation ->
            val ref = database.getReference("Comments")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fetchedComments = mutableListOf<Comment>()
                    for (commentSnapshot in snapshot.children) {
                        val comment = commentSnapshot.getValue(Comment::class.java)
                        if (comment != null) {
                            comment.id = commentSnapshot.key
                            fetchedComments.add(comment)
                        }
                    }
                    //Updating local copy
                    comments = fetchedComments
                    continuation.resume(fetchedComments)
                }
                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWithException(error.toException())
                }
            })
        }
    }

    override fun getComment(id: String): Comment? {
        return comments.find { it.id == id }
    }

    override fun createComment(comment: Comment): Boolean {
        return try {
            val uid = UUID.randomUUID().toString()
            val newId = UUID.randomUUID().toString()

            val commentsTable = database.getReference("Comments").child(uid).child(newId)
            commentsTable.setValue(comment)
                .addOnSuccessListener {
//                    Show Dialog confirmation
                }
                .addOnFailureListener { error ->
//                    Show Dialog Error
                }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun editComment(uid: String, idComment: String, comment: Comment): Boolean {
        return try {
            val commentsTable = database.getReference("Comments").child(uid).child(idComment)
            commentsTable.setValue(comment)
                .addOnSuccessListener {
//                    Show Dialog confirmation
                }
                .addOnFailureListener { error ->
//                    Show Dialog Error
                }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun deleteComment(uid: String, idComment: String): Boolean {
        return try {
            val commentsTable = database.getReference("Comments").child(uid).child(idComment)
            commentsTable.removeValue()
                .addOnSuccessListener {
//                    Show Dialog confirmation
                }
                .addOnFailureListener { error ->
//                    Show Dialog Error
                }
            true
        } catch (e: Exception) {
            false
        }
    }
}