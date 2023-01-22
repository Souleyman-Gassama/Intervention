package com.athand.intervention.data.database.remote.api

import com.athand.intervention.data.entity.MyCompany
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

/**
 * Cree le 31/12/2022 par Gassama Souleyman
 */
interface MyCompanyApi {
    fun get_Company(authorUID: String): Task<QuerySnapshot>
    fun set_Company(myCompany: MyCompany): Task<Void>
    fun delete_Company(myCompany: MyCompany): Task<Void>
}