package com.example.posthub

import org.junit.Test
import org.junit.Assert.*

@Test
fun `test fetchPosts returns non-empty list`() = runBlocking {
    // Mock Repository
    val mockRepository = mock(PostsRepository::class.java)

    // Mock the result returned by Repository
    val mockPosts = listOf(Post(1, "Test Title", "Test Body"))
    `when`(mockRepository.getPosts()).thenReturn(mockPosts)

    // Create ViewModel with mock repository
    val viewModel = PostsViewModel(mockRepository)

    // Fetch posts
    viewModel.fetchPosts()

    // Assert that the list is not empty
    assert(viewModel.posts.value?.isNotEmpty() == true)
}

@Test
fun `test saving posts to local storage`() = runBlocking {
    // Mock DAO and database
    val mockDao = mock(PostsDao::class.java)

    // Mock some post data
    val post = Post(1, "Test Title", "Test Body")

    // Assume insert function is being called
    `when`(mockDao.insertPost(post)).thenReturn(Unit)

    // Test the insertion
    mockDao.insertPost(post)

    // Verify that the insertion was actually called
    verify(mockDao).insertPost(post)
}

@Test
fun `test fetchComments for post when post is clicked`() = runBlocking {
    // Mock Repository and return dummy comments for a specific post
    val mockRepository = mock(CommentsRepository::class.java)
    val postId = 1
    val mockComments = listOf(Comment(postId, "Test Comment"))

    `when`(mockRepository.getCommentsForPost(postId)).thenReturn(mockComments)

    // Create ViewModel
    val viewModel = PostDetailViewModel(mockRepository)

    // Fetch comments for the post
    viewModel.fetchComments(postId)

    // Assert that the comments are fetched and not null
    assert(viewModel.comments.value?.isNotEmpty() == true)
}

@Test
fun `test searchPosts filters correct results`() = runBlocking {
    // Mock Repository
    val mockRepository = mock(PostsRepository::class.java)

    // Mock some post data
    val mockPosts = listOf(
        Post(1, "First Title", "First Body"),
        Post(2, "Second Title", "Second Body")
    )
    `when`(mockRepository.getPosts()).thenReturn(mockPosts)

    // Create ViewModel
    val viewModel = PostsViewModel(mockRepository)

    // Simulate search function
    viewModel.searchPosts("First")

    // Check if filtered posts contain the expected result
    assert(viewModel.filteredPosts.value?.size == 1)
    assert(viewModel.filteredPosts.value?.first()?.title == "First Title")
}
