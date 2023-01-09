package com.amaa.write.database.userinformation

import android.util.Log
import androidx.lifecycle.LiveData

class RegisterRepository(private val dao: RegisterDatabaseDao) {

    val users = dao.getAllUsers()
    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun update(user: RegisterEntity) {
        return dao.update(user)
    }
    suspend fun getUserName(userName: String):RegisterEntity?{
        return dao.getUsername(userName)
    }


}