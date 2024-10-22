package com.example.posthub.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts:List<PostEntity>)

    @Query("SELECT * FROM posts")
    fun getPosts(): LiveData<List<PostEntity>>
}


