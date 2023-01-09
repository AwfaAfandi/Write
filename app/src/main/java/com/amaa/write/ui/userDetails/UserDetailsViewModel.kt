package com.amaa.write.ui.userDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.amaa.write.database.posts.PostsEntity
import com.amaa.write.database.posts.PostsRepository
import com.amaa.write.database.userinformation.RegisterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserDetailsViewModel (private val repository: PostsRepository, application: Application):AndroidViewModel(application){
    lateinit var email : String
    lateinit var firstName : String
    lateinit var lastName : String



    val users = repository.posts

    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    fun doneNavigating(){
        _navigateto.value=false
    }

    fun deletepost(id: PostsEntity): Job = viewModelScope.launch {
        repository.deletePosts(postid = id )
    }



}