package com.amaa.write.ui.updateprofile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amaa.write.database.userinformation.RegisterRepository
import java.lang.IllegalArgumentException

class UpdateProfileViewModelFactory(
    private  val repository: RegisterRepository,
    private val application: Application):ViewModelProvider.Factory{
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(UpdateProfileViewModel::class.java)) {
           return UpdateProfileViewModel(repository, application) as T
       }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}