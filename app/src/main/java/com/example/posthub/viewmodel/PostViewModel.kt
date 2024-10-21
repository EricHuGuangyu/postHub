package com.example.posthub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.data.local.PostEntity
import com.example.posthub.data.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    //val posts: LiveData<List<PostEntity>> = repository.posts

    private val _posts = MutableLiveData<List<PostEntity>>()
    val posts: LiveData<List<PostEntity>> get() = _posts

    fun refreshPosts() {
        viewModelScope.launch {
            try {
                // Get data from network and save to local storage
                _posts.value = repository.refreshPosts()
                // read data from local storage
                val postList = repository.fetchPostsFromLocal()
                //_posts.value = postList
            } catch (e: Exception) {
                Log.e("PostViewModel", e.printStackTrace().toString())
            }
        }
    }
}
