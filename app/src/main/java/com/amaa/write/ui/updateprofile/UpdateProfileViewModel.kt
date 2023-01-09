package com.amaa.write.ui.updateprofile

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.provider.SyncStateContract.Helpers.insert
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.amaa.write.database.userinformation.RegisterEntity
import com.amaa.write.database.userinformation.RegisterRepository
import kotlinx.coroutines.*
import java.nio.file.Files.delete


class UpdateProfileViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

lateinit var inputUsername  : String



    var userDetailsLiveData = MutableLiveData<Array<RegisterEntity>>()

    @Bindable
    val inputFirstName = MutableLiveData<String?>()

    @Bindable
    val inputLastName = MutableLiveData<String?>()

    @Bindable
    val inputPassword = MutableLiveData<String?>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun updateButton() {

        if (inputFirstName.value == null || inputLastName.value == null ||  inputPassword.value == null || inputUsername == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                    val usersNames = repository.getUserName(inputUsername)
                    if (usersNames == null) {
                    _errorToastUsername.value = true
                } else {



                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val password = inputPassword.value!!
                        var user = RegisterEntity(userId =usersNames.userId ,firstName = firstName, lastName = lastName, passwrd = password, userName = usersNames.userName)
                    update(RegisterEntity(user.userId,user.firstName,user.lastName,user.userName,user.passwrd))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputPassword.value = null
                    _navigateto.value = true




                }
            }
        }



    }

    fun deleteAccount(context : Context) {


        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete :")
        builder.setMessage("Are you sure you want to delete your account ?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            uiScope.launch {
                val usersNames = repository.getUserName(inputUsername)





                    var user = usersNames?.let { RegisterEntity(userId = it.userId ,firstName = usersNames.firstName, lastName = usersNames.lastName, passwrd = usersNames.passwrd, userName = usersNames.userName) }
                if (user != null) {
                    delete(RegisterEntity(user.userId,user.firstName,user.lastName,user.userName,user.passwrd))
                }
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputPassword.value = null
                    _navigateto.value = true





            }



            Toast.makeText(context, "delete account clicked", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()

    }



    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
    }

    fun donetoast() {
        _errorToast.value = false
      }

    fun donetoastUserName() {
        _errorToast.value = false
      }

    private fun update(user: RegisterEntity): Job = viewModelScope.launch {
        repository.update(user)
    }

    private fun delete(user: RegisterEntity): Job = viewModelScope.launch {
        repository.delete(user)
    }




    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}





