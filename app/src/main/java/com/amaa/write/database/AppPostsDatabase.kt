package com.amaa.write.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amaa.write.database.posts.PostsDatabaseDao
import com.amaa.write.database.posts.PostsEntity


@Database(entities = [PostsEntity::class], version = 1, exportSchema = false)
abstract class AppPostsDatabase : RoomDatabase() {

    abstract val postsDatabaseDao: PostsDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AppPostsDatabase? = null


        fun getInstance(context: Context): AppPostsDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppPostsDatabase::class.java,
                        "posts_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
