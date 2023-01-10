package com.amaa.write.ui.login

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amaa.write.database.userinformation.RegisterEntity
import com.amaa.write.database.userinformation.RegisterRepository
import com.amaa.write.ui.post.PostFragment
import com.amaa.write.ui.userDetails.UserDetailsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {
     var usersNames : RegisterEntity? = null

    @Bindable
    val inputUsername = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigatetoRegister = MutableLiveData<Boolean>()

    val navigatetoRegister: LiveData<Boolean>
        get() = _navigatetoRegister

    private val _navigatetoUserDetails = MutableLiveData<Boolean>()

    val navigatetoUserDetails: LiveData<Boolean>
        get() = _navigatetoUserDetails

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()

    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword


    fun signUP() {
        _navigatetoRegister.value = true
    }

    fun loginButton() {
        if (inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {

                 usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    if(usersNames?.passwrd == inputPassword.value){
                        inputPassword.value = null
                       _navigatetoUserDetails.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }

    }




    fun doneNavigatingRegiter() {
        _navigatetoRegister.value = false
    }

    fun doneNavigatingUserDetails() {
        _navigatetoUserDetails.value = false
    }


    fun donetoast() {
        _errorToast.value = false
        }


    fun donetoastErrorUsername() {
        _errorToastUsername .value = false
        }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false
         }



    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}