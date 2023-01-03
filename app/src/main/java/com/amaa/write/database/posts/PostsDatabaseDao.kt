package com.amaa.write.database.posts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostsDatabaseDao {

    @Insert
    suspend fun insert(posts: PostsEntity)


    @Query("SELECT * FROM posts_table")
    fun getAllPosts(): LiveData<List<PostsEntity>>

    @Query("DELETE FROM posts_table")
    suspend fun deleteAll(): Int




}