package com.amaa.write.database.posts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostsDatabaseDao {

    @Insert
    suspend fun insert(posts: PostsEntity)


    @Query("SELECT * FROM posts_table ORDER BY postid DESC")
    fun getAllPosts(): LiveData<List<PostsEntity>>

    @Delete
    suspend fun delete(postid: PostsEntity )


}