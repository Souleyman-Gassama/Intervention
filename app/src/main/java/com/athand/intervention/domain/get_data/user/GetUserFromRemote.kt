package com.athand.intervention.domain.get_data.user

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.domain.CustumLifeCycleOwner
import com.athand.intervention.domain.get_data.ResultOfGetData

/**
 * Cree le 15/01/2023 par Gassama Souleyman
 */
internal class GetUserFromRemote(
    private val baseRemoteRepository: BaseRemoteRepository,
    private val uid: String, var result_Get_User: (ResultOfGetData) -> Unit) {

    init {
        execute()
    }

    fun execute() {
        get_User_Object_From_FireBase()
    }


    fun get_User_Object_From_FireBase() {
        if (uid != "") {
            baseRemoteRepository.get_User(uid)
                .addOnSuccessListener {
                    val currentUser = it.toObject(MyUser::class.java)!!
                    result_Get_User(ResultOfGetData(true, currentUser, null))

                }
                .addOnFailureListener {
                    result_Get_User(ResultOfGetData(false, null, it.message))
                }
        }else{
            result_Get_User(ResultOfGetData(false, null, ""))
        }
    }
}