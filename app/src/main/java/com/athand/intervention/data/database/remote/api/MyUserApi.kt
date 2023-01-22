package com.athand.intervention.data.database.remote.api

import com.athand.intervention.data.entity.MyUser
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Cree le 30/12/2022 par Gassama Souleyman
 */
interface MyUserApi {
    fun get_User(uid: String): Task<DocumentSnapshot>
    fun set_User(myUser: MyUser): Task<Void>
    fun delete_User(myUser: MyUser): Task<Void>
}