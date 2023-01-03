package com.amaa.write.ui.userDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amaa.write.database.posts.PostsRepository
import com.amaa.write.database.userinformation.RegisterRepository

class UserDetailsViewModel (private val repository: PostsRepository, application: Application):AndroidViewModel(application){

    val users = repository.posts

    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    fun doneNavigating(){
        _navigateto.value=false
    }

    fun backButtonclicked(){
        _navigateto.value = true

    }



}