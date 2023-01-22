package com.athand.intervention.data.repository.local

import androidx.lifecycle.LiveData
import com.athand.intervention.data.database.local.dao.MyCompanyDao
import com.athand.intervention.data.entity.MyCompany

class CompanyLocalRepository(var myCompanyDao: MyCompanyDao) {

    /**
     * CREATE ____________________________________________________________________________________________
     */
    fun inset_Company(company: MyCompany?) {
        myCompanyDao.inset_Company(company)
    }

    fun inset_List_Company(companys: MutableList<MyCompany>?) {
        myCompanyDao.inset_List_Company(companys)
    }

    /**
     * GET _______________________________________________________________________________________________
     */
    fun get_All_Company(userUID: String): LiveData<MutableList<MyCompany>>? {
        return myCompanyDao.get_All_Company(userUID)
    }

    /**
     * UPDATE ____________________________________________________________________________________________
     */
    fun update_Company(company: MyCompany?) {
        myCompanyDao.update_Company(company)
    }

    /**
     * DELETE ____________________________________________________________________________________________
     */
    fun delete_Company(id: String) {
        myCompanyDao.delete_Company(id)
    }
}