package com.athand.intervention.domain.get_data.company

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.domain.CustumLifeCycleOwner
import com.athand.intervention.domain.get_data.ResultOfGetData

/**
 * Cree le 15/01/2023 par Gassama Souleyman
 */
internal class GetCompanyFromDatabase(
    private val baseRemoteRepository: BaseRemoteRepository,
    private val baseLocalRepository: BaseLocalRepository,
    private val uid: String, var result_Of_Get_Company: (ResultOfGetData) -> Unit
) {

    lateinit var getCompanyFromLocal: GetCompanyFromLocal

    init {
        execute()
    }

    fun execute() {
        get_Companys_Object_From_Local_Database()
    }

    fun stop_Observe_Data() {
        getCompanyFromLocal.stop_Observing_Data()
    }

    private fun get_Companys_Object_From_Local_Database() {
        getCompanyFromLocal = GetCompanyFromLocal(baseLocalRepository, uid) { result ->
            if (result.success) {
                result_Of_Get_Company(result)
            } else {
                get_Companys_Object_From_FireBase()
            }
        }
    }

    fun get_Companys_Object_From_FireBase() {
        GetCompanyFromRemote(baseRemoteRepository, uid) { result ->
            if (result.success) {
                val currentCompanyList = result.data as MutableList<MyCompany>
                set_Conpanys_Object_To_Local_Database(currentCompanyList)
            }
            result_Of_Get_Company(ResultOfGetData(false, null, result.message))
        }
    }

    private fun set_Conpanys_Object_To_Local_Database(companyList: MutableList<MyCompany>) {
        baseLocalRepository.set_List_Company(companyList)
    }

}