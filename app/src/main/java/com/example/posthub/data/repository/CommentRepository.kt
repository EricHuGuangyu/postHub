package com.example.posthub.data.repository

import androidx.lifecycle.LiveData
import com.example.posthub.data.local.CommentDao
import com.example.posthub.data.local.CommentEntity
import com.example.posthub.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(private val commentDao: CommentDao, private val apiService: ApiService) {

    //get comments for server and insert into local database
    suspend fun refreshComments(postId: Int) {
        val commentsList = apiService.getCommentsForPost(postId).map {
            CommentEntity(it.id, it.postId, it.name, it.email, it.body)
        }
        commentDao.insertComments(commentsList)
    }

    // get comments from postId
     fun fetchCommentsForPost(postId: Int): LiveData<List<CommentEntity>> {
        return commentDao.getComments(postId)
    }
}
