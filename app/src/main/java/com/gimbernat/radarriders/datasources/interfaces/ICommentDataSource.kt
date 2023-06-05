package com.gimbernat.radarriders.datasources.interfaces

import com.gimbernat.radarriders.models.Comment

interface ICommentDataSource {

    suspend fun fetch(): List<Comment>
    fun getComment(id: String): Comment?
    fun createComment(comment: Comment): Boolean
    fun editComment(comment: Comment): Boolean
    fun deleteComment(id: String): Boolean

}