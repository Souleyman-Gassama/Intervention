package com.athand.intervention.data.repository

import com.athand.intervention.data.database.remote.api.MyCompanyApi
import com.athand.intervention.data.database.remote.api.MyUserApi
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

/**
 * Cree le 30/12/2022 par Gassama Souleyman
 */
class BaseRemoteRepository(val myUserApi: MyUserApi, val myCompanyApi: MyCompanyApi) {

    /**
     * USER _____________________________________________________
     */
    fun get_User(uid: String): Task<DocumentSnapshot> {
        return myUserApi.get_User(uid)
    }

    fun set_User(myUser: MyUser): Task<Void>{
        return myUserApi.set_User(myUser)
    }

    fun delete_User(myUser: MyUser): Task<Void> {
        return myUserApi.delete_User(myUser)
    }

    /**
     * COMPANY _____________________________________________________
     */
    fun get_Company(authorUID: String): Task<QuerySnapshot> {
        return myCompanyApi.get_Company(authorUID)
    }

    fun set_Company(myCompany: MyCompany): Task<Void>{
        return myCompanyApi.set_Company(myCompany)
    }

    fun delete_Company(myCompany: MyCompany): Task<Void> {
        return myCompanyApi.delete_Company(myCompany)
    }
}