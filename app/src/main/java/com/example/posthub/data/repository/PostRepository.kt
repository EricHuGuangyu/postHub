package com.example.posthub.data.repository

import androidx.lifecycle.LiveData
import com.example.posthub.data.local.PostDao
import com.example.posthub.data.local.PostEntity
import com.example.posthub.data.remote.ApiService

class PostRepository(private val postDao: PostDao, private val apiService: ApiService) {

    val posts: LiveData<List<PostEntity>> = postDao.getPosts()

    suspend fun refreshPosts() {
        val postList = apiService.getPosts().map { PostEntity(it.id, it.title, it.body) }
        postDao.insertPosts(postList)
    }
}
