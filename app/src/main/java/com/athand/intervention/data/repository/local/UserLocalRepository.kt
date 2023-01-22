package com.athand.intervention.data.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.athand.intervention.data.database.local.dao.MyUserDao
import com.athand.intervention.data.entity.MyUser

class UserLocalRepository(var userDao: MyUserDao?) {
    /**
     * CREATE ____________________________________________________________________________________________
     */
    fun inset_MyUser(myUser: MyUser?) {
        userDao?.inset_User(myUser)
    }
    /**
     * GET _______________________________________________________________________________________________
     */
    fun get_MyUser(userUID: String): LiveData<MyUser>? {
        return userDao?.get_User(userUID)
    }
    /**
     * UPDATE ____________________________________________________________________________________________
     */
    fun update_MyUser(myUser: MyUser?) {
        userDao?.update_User(myUser)
    }

  /**
     * DELETE ____________________________________________________________________________________________
     */
    fun delete_MyUser(userUID: String) {
        userDao?.delete_User(userUID)
    }
}