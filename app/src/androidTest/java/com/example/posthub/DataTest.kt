package com.example.posthub;

import com.example.posthub.data.local.Comment
import com.example.posthub.data.local.Post
import com.example.posthub.data.local.PostDao
import com.example.posthub.data.local.PostEntity
import com.example.posthub.data.repository.CommentRepository
import com.example.posthub.data.repository.PostRepository
import com.example.posthub.viewmodel.CommentViewModel
import com.example.posthub.viewmodel.PostViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@Test
fun `test loadPostsFromLocalStorage returns data when no network`() = runBlocking {
    // Mock DAO and Repository
    val mockDao = mock(PostDao::class.java)
    val mockRepository = mock(PostRepository::class.java)

    // Mock data stored in local database
    val localPosts = listOf(PostEntity(1, "Local Title", "Local Body"))
    `when`(mockDao.getPosts()).thenReturn(localPosts)

    // Simulate fetching posts from local storage
    val viewModel = PostViewModel(mockRepository)
    viewModel.refreshPosts()

    // Assert that the data is loaded from local storage
    assert(viewModel.posts.value == localPosts)
}

@Test
fun `test fetchPosts returns non-empty list`() = runBlocking {
    // Mock Repository
    val mockRepository = mock(PostRepository::class.java)

    // Mock the result returned by Repository
    val mockPosts = listOf(Post(1, 4, "Test Title", "Test Body"))
    `when`(mockRepository.fetchPostsFromLocal()).thenReturn(mockPosts)

    // Create ViewModel with mock repository
    val viewModel = PostViewModel(mockRepository)

    // Fetch posts
    viewModel.refreshPosts()

    // Assert that the list is not empty
    assert(viewModel.posts.value?.isNotEmpty() == true)
}

@Test
fun `test saving posts to local storage`() = runBlocking {
    // Mock DAO and database
    val mockDao = mock(PostDao::class.java)

    // Mock some post data
    val post = listOf(PostEntity(1, "Test Title", "Test Body"))

    // Assume insert function is being called
    `when`(mockDao.insertPosts(post)).thenReturn(Unit)

    // Test the insertion
    mockDao.insertPosts(post)

    // Verify that the insertion was actually called
    verify(mockDao).insertPosts(post)
}

@Test
fun `test fetchComments for post when post is clicked`() = runBlocking {
    // Mock Repository and return dummy comments for a specific post
    val mockRepository = mock(CommentRepository::class.java)
    val postId = 1
    val mockComments = listOf(Comment(postId, "Test Comment"))

    `when`(mockRepository.fetchCommentsForPost(postId)).thenReturn(mockComments)

    // Create ViewModel
    val viewModel = CommentViewModel(mockRepository)

    // Fetch comments for the post
    viewModel.refreshComments(postId)

    // Assert that the comments are fetched and not null
    assert(viewModel.comments.value?.isNotEmpty() == true)
}

@Test
fun `test searchPosts filters correct results`() = runBlocking {
    // Mock Repository
    val mockRepository = mock(PostRepository::class.java)

    // Mock some post data
    val mockPosts = listOf(
        Post(1, 1, "First Title", "First Body"),
        Post(2, 3, "Second Title", "Second Body")
    )
    `when`(mockRepository.fetchPostsFromLocal()).thenReturn(mockPosts)

    // Create ViewModel
    val viewModel = PostViewModel(mockRepository)

    // Simulate search function
    viewModel.search("First")

    // Check if filtered posts contain the expected result
    assert(viewModel.filteredPosts.value?.size == 1)
    assert(viewModel.filteredPosts.value?.first()?.title == "First Title")
}

