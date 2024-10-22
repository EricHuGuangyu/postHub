package com.example.posthub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.data.local.CommentEntity
import com.example.posthub.data.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(private val repository: CommentRepository) :
    ViewModel() {
    private val _comments = MutableLiveData<List<CommentEntity>>()
    val comments: LiveData<List<CommentEntity>> get() = _comments

    fun refreshComments(postId: Int) {
        viewModelScope.launch {
            try {
                // get data from server and save in local db
                repository.refreshComments(postId)
                repository.fetchCommentsForPost(postId).observeForever { commentsList ->
                    _comments.value = commentsList
                }
            } catch (e: Exception) {
                Log.e("CommentViewModel", "Error refreshing comments: ${e.message}")
            }
        }
    }
}
