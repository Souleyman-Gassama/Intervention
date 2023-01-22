package com.athand.intervention.domain.get_data.company

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.domain.CustumLifeCycleOwner
import com.athand.intervention.domain.get_data.ResultOfGetData

/**
 * Cree le 21/01/2023 par Gassama Souleyman
 */
internal class GetCompanyFromLocal(
    private val baseLocalRepository: BaseLocalRepository,
    private val uid: String, var result_Of_Get_Company: (ResultOfGetData) -> Unit
) {
    private var custumLifeCycleOwner: CustumLifeCycleOwner

    init {
        custumLifeCycleOwner = CustumLifeCycleOwner()
        execute()
    }

    fun execute() {
        get_Companys_Object_From_Local_Database()
    }

    fun stop_Observing_Data() {
        custumLifeCycleOwner.stop()
    }

    private fun get_Companys_Object_From_Local_Database() {
        baseLocalRepository.get_All_Company(uid)?.observe(custumLifeCycleOwner) {
            if (it != null && it.size > 0) {
                result_Of_Get_Company(ResultOfGetData(true, it, null))
            } else {
                result_Of_Get_Company(ResultOfGetData(false, null, ""))
            }
        }
    }
}