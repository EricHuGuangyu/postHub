package com.example.posthub.data.remote

import com.example.posthub.data.local.Comment
import com.example.posthub.data.local.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsForPost(@Path("postId") postId: Int): List<Comment>
}
