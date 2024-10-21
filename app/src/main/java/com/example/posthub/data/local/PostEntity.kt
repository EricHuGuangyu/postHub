package com.example.posthub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String
)
