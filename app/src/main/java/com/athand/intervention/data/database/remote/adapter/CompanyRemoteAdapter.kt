package com.athand.intervention.data.database.remote.adapter

import com.athand.intervention.data.database.remote.api.MyCompanyApi
import com.athand.intervention.data.entity.MyCompany
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Cree le 31/12/2022 par Gassama Souleyman
 */
class CompanyRemoteAdapter: MyCompanyApi {

    val firestore = Firebase.firestore

    override fun get_Company(authorUID: String): Task<QuerySnapshot> {
        return firestore
            .collection("AllData")
            .document("Companys")
            .collection(authorUID)
            .get()
    }

    override fun set_Company(myCompany: MyCompany): Task<Void> {
        return firestore
            .collection("AllData")
            .document("Companys")
            .collection(myCompany.uidAuthor)
            .document(myCompany.id)
            .set(myCompany)
    }

    override fun delete_Company(myCompany: MyCompany): Task<Void> {
        return firestore
            .collection("AllData")
            .document("Companys")
            .collection(myCompany.uidAuthor)
            .document(myCompany.id)
            .delete()
    }

}