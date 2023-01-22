package com.athand.intervention.domain.set_data

import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.entity.MyUser
/**
 * Cree le 21/01/2023 par Gassama Souleyman
 */
internal class SetUserToRemote(
    val baseRemoteRepository: BaseRemoteRepository,
    val user: MyUser, var result_Of_Set_User: (ResultOfSetData) -> Unit) {

    init {
        execute()
    }

    fun execute() {
        set_User_To_FireBase()
    }

    private fun set_User_To_FireBase() {
        baseRemoteRepository.set_User(user)
            .addOnSuccessListener{
                result_Of_Set_User(ResultOfSetData(true, null))
            }.addOnFailureListener{
                result_Of_Set_User(ResultOfSetData(false, it.message))
            }
    }
}