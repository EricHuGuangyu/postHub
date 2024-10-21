package com.example.posthub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.data.local.PostEntity
import com.example.posthub.data.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    val posts: LiveData<List<PostEntity>> = repository.posts

    fun refreshPosts() {
        viewModelScope.launch {
            repository.refreshPosts()
        }
    }
}
