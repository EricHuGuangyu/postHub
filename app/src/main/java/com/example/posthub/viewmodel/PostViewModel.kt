package com.example.posthub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.data.local.PostEntity
import com.example.posthub.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {
    private val _posts = MutableLiveData<List<PostEntity>>()
    val posts: LiveData<List<PostEntity>> get() = _posts

    fun refreshPosts() {
        viewModelScope.launch {
            try {
                // get data from server and store in local
                repository.refreshPosts()
                repository.fetchPostsFromLocal().observeForever { postList ->
                    _posts.value = postList
                }
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error refreshing posts: ${e.message}")
            }
        }
    }
}
