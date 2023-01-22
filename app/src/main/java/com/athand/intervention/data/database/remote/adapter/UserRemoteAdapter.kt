package com.athand.intervention.data.database.remote.adapter

import com.athand.intervention.data.database.remote.api.MyUserApi
import com.athand.intervention.data.entity.MyUser
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Cree le 30/12/2022 par Gassama Souleyman
 */
class UserRemoteAdapter: MyUserApi {
    val firestore = Firebase.firestore

    override fun get_User(uid: String): Task<DocumentSnapshot>{
        return firestore
            .collection("AllData")
            .document("users")
            .collection("Fr")
            .document(uid)
            .get()
    }

    override fun set_User(myUser: MyUser): Task<Void> {
        return firestore
            .collection("AllData")
            .document("users")
            .collection("Fr")
            .document(myUser.uid)
            .set(myUser)
    }

    override fun delete_User(myUser: MyUser): Task<Void>  {
        return firestore
            .collection("AllData")
            .document("users")
            .collection("Fr").document(myUser.uid)
            .delete()
    }
}