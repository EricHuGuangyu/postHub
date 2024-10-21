package com.example.posthub.data.repository

import androidx.lifecycle.LiveData
import com.example.posthub.data.local.CommentDao
import com.example.posthub.data.local.CommentEntity
import com.example.posthub.data.remote.ApiService

class CommentRepository(private val commentDao: CommentDao, private val apiService: ApiService) {

    fun getComments(postId: Int): LiveData<List<CommentEntity>> = commentDao.getComments(postId)

    suspend fun refreshComments(postId: Int) {
        val commentsList = apiService.getCommentsForPost(postId).map {
            CommentEntity(it.id, it.postId, it.name, it.email, it.body)
        }
        commentDao.insertComments(commentsList)
    }

    // 根据 postId 获取评论
    suspend fun fetchCommentsForPost(postId: Int): LiveData<List<CommentEntity>> {
        return commentDao.getComments(postId)
    }
}
