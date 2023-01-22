package com.athand.intervention.domain.get_data.company

import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.domain.get_data.ResultOfGetData

/**
 * Cree le 21/01/2023 par Gassama Souleyman
 */
internal class GetCompanyFromRemote(
    private val baseRemoteRepository: BaseRemoteRepository,
    private val uid: String, var result_Get_Company: (ResultOfGetData) -> Unit
) {

    init {
        execute()
    }

    fun execute() {
        get_Companys_Object_From_FireBase()
    }

    fun get_Companys_Object_From_FireBase() {
        baseRemoteRepository.get_Company(uid)
            .addOnSuccessListener {
                if (it.documents != null || it.documents?.size!! > 0) {
                    var currentCompanyList: MutableList<MyCompany> = mutableListOf()
                    for (doc in it.documents) {
                        currentCompanyList.add(doc.toObject(MyCompany::class.java)!!)
                    }
                    result_Get_Company(ResultOfGetData(true, currentCompanyList, null))

                } else {
                    result_Get_Company(ResultOfGetData(false, null, ""))

                }
            }
    }

}