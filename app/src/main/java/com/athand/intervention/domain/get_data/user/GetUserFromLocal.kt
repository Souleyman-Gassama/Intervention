package com.athand.intervention.domain.get_data.user

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.domain.CustumLifeCycleOwner
import com.athand.intervention.domain.get_data.ResultOfGetData

/**
 * Cree le 15/01/2023 par Gassama Souleyman
 */
internal class GetUserFromLocal(
    private val baseLocalRepository: BaseLocalRepository,
    private val uid: String, var result_Of_Get_User: (ResultOfGetData) -> Unit) {

    private var custumLifeCycleOwner: CustumLifeCycleOwner

    init {
        custumLifeCycleOwner = CustumLifeCycleOwner()
        execute()
    }

    fun execute() {
        get_User_Object_From_Local_Database()
    }

    private fun get_User_Object_From_Local_Database() {
        baseLocalRepository.get_User(uid)?.observe(custumLifeCycleOwner){
            if (it != null) {
                result_Of_Get_User(ResultOfGetData(true, it, null))
            } else {
                result_Of_Get_User(ResultOfGetData(false, null, null))
            }
            custumLifeCycleOwner.stop()
            custumLifeCycleOwner.desroy()
        }
    }
}