package com.athand.intervention.domain.set_data

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.entity.MyUser
/**
 * Cree le 21/01/2023 par Gassama Souleyman
 */
internal class SetUserToLocal(
    val baseLocalRepository: BaseLocalRepository,
    val user: MyUser, var result_Of_Set_User: (ResultOfSetData) -> Unit) {

    init {
        execute()
    }

    fun execute() {
        set_User_To_Room()
        result_Of_Set_User(ResultOfSetData(true, null))
    }

    private fun set_User_To_Room() {
        baseLocalRepository.set_User(user)
    }
}