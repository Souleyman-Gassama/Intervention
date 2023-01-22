package com.athand.intervention.domain.get_data.user

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.domain.CustumLifeCycleOwner
import com.athand.intervention.domain.get_data.ResultOfGetData

/**
 * Cree le 15/01/2023 par Gassama Souleyman
 */
internal class GetUserFromDatabase(
    private val baseRemoteRepository: BaseRemoteRepository,
    private val baseLocalRepository: BaseLocalRepository,
    private val uid: String, var result_Of_Get_User: (ResultOfGetData) -> Unit
) {

    init {
        execute()
    }

    fun execute() {
        get_User_Object_From_Local_Database()
    }

    private fun get_User_Object_From_Local_Database() {
        GetUserFromLocal(baseLocalRepository, uid) { result ->
            if (result.success) {
                result_Of_Get_User(result)
            } else {
                get_User_Object_From_FireBase()
            }
        }

    }

    fun get_User_Object_From_FireBase() {
        GetUserFromRemote(baseRemoteRepository, uid) { result ->
            if (result.success) {
                val currentUser = result.data as MyUser
//          Note: set_User_Object_To_Local_Database
                baseLocalRepository.set_User(currentUser)
            }

            result_Of_Get_User(result)
        }
    }
}