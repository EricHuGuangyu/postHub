package com.example.posthub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.posthub.data.local.AppDatabase
import com.example.posthub.data.remote.ApiService
import com.example.posthub.data.repository.PostRepository
import com.example.posthub.viewmodel.PostViewModel
import com.example.posthub.viewmodel.PostViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel
    private lateinit var repository: PostRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postDao = AppDatabase.getDatabase(application).postDao()
        val retrofitService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        repository = PostRepository(postDao, retrofitService)

        val factory = PostViewModelFactory(repository)
        postViewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)

        postViewModel.refreshPosts()
        postViewModel.posts.observe(this, Observer { posts ->
            // Update UI with posts
        })
    }
}