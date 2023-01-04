package com.amaa.write.database.posts

import android.util.Log
import androidx.lifecycle.LiveData
import com.amaa.write.database.posts.PostsDatabaseDao
import com.amaa.write.database.posts.PostsEntity


class PostsRepository(private val dao: PostsDatabaseDao) {

    val posts = dao.getAllPosts()
    suspend fun insert(user: PostsEntity) {
        return dao.insert(user)
    }

    suspend fun getPosts(userName: String): LiveData<List<PostsEntity>> {
        Log.i("MYTAG", "inside Repository Getusers fun ")
        return dao.getAllPosts()
    }

    suspend fun deletePosts(postid: PostsEntity)=dao.delete(postid)

}