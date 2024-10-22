package com.example.posthub.data.repository

import androidx.lifecycle.LiveData
import com.example.posthub.data.local.PostDao
import com.example.posthub.data.local.PostEntity
import com.example.posthub.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(private val postDao: PostDao, private val apiService: ApiService) {

    //get posts for server and insert into local database
    suspend fun refreshPosts() {
        val postList = apiService.getPosts().map { PostEntity(it.id, it.title, it.body) }
        postDao.insertPosts(postList)
    }

    // Get data from local database
    fun fetchPostsFromLocal(): LiveData<List<PostEntity>> {
        return postDao.getPosts()
    }

}
