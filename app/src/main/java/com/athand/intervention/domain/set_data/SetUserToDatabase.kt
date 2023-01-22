package com.athand.intervention.domain.set_data

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.entity.MyUser
/**
 * Cree le 15/01/2023 par Gassama Souleyman
 */
internal class SetUserToDatabase(
    val baseRemoteRepository: BaseRemoteRepository,
    val baseLocalRepository: BaseLocalRepository,
    val user: MyUser, var result_Of_Set_User: (ResultOfSetData) -> Unit) {

    init {
        execute()
    }

    fun execute() {
        set_User_To_FireBase()
    }

    private fun set_User_To_FireBase() {
        SetUserToRemote(baseRemoteRepository, user){
            if (it.success) {
               set_User_To_Local()
            }else{
                result_Of_Set_User(ResultOfSetData(false, null))
            }
        }
    }

    private fun set_User_To_Local() {
        SetUserToLocal(baseLocalRepository, user){
            result_Of_Set_User(ResultOfSetData(true, null))
        }
    }
}