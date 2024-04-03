package com.cleanarchitectureexample.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cleanarchitectureexample.domain.model.PostResponse

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    suspend fun getAll(): List<PostResponse>

    @Query("DELETE FROM posts")
    suspend fun deleteAllCourses()

    @Insert
    suspend fun insert(post: PostResponse)

    @Update
    suspend fun update(post: PostResponse)

    @Delete
    suspend fun delete(post: PostResponse)
}