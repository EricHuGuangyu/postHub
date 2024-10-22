package com.example.posthub;

import com.example.posthub.data.local.Post
import com.example.posthub.data.local.PostDao
import com.example.posthub.data.local.PostEntity
import com.example.posthub.data.repository.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@Test
fun `test loadPostsFromLocalStorage returns data when no network`() = runBlocking {
    // Mock DAO and Repository
    val mockDao = mock(PostDao::class.java)
    val mockRepository = mock(PostRepository::class.java)

    // Mock data stored in local database
    val localPosts = listOf(PostEntity(1, 1,"Local Title", "Local Body"))
    `when`(mockDao.getPosts()).thenReturn(localPosts)

    // Simulate fetching posts from local storage
    val viewModel = PostsViewModel(mockRepository)
    viewModel.loadPostsFromLocalStorage()

    // Assert that the data is loaded from local storage
    assert(viewModel.posts.value == localPosts)
}
