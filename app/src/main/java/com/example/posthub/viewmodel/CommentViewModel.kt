package com.example.posthub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posthub.data.local.CommentEntity
import com.example.posthub.data.repository.CommentRepository
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: CommentRepository) : ViewModel() {
    fun getComments(postId: Int): LiveData<List<CommentEntity>>? {
        return null;
    }

    fun refreshComments(postId: Int) {

    }
}
