package com.amaa.write.ui.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amaa.write.database.posts.PostsRepository
import java.lang.IllegalArgumentException

class PostViewModelFactory(private val repository: PostsRepository,
                           private val application: Application
): ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostFragmentViewModel::class.java)) {
            return PostFragmentViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}