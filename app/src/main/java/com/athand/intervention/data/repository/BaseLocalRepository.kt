package com.athand.intervention.data.repository

import androidx.lifecycle.LiveData
import com.athand.intervention.data.repository.local.CompanyLocalRepository
import com.athand.intervention.data.repository.local.UserLocalRepository
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
import java.util.concurrent.Executor

/**
 * Cree le 30/12/2022 par Gassama Souleyman
 */
class BaseLocalRepository(
    val myUserRepository: UserLocalRepository,
    val myCompanyRepository: CompanyLocalRepository,
    val executor: Executor
    ) {

    /**
     * USER _____________________________________________________
     */
    fun get_User(uid: String): LiveData<MyUser>? {
        return myUserRepository.get_MyUser(uid)
    }

    fun set_User(myUser: MyUser){
        executor.execute {   myUserRepository.inset_MyUser(myUser)  }
    }

    fun delete_User(id: String){
        myUserRepository.delete_MyUser(id)
    }

    /**
     * COMPANY _____________________________________________________
     */
    fun get_All_Company(authorUID: String): LiveData<MutableList<MyCompany>>? {
        return myCompanyRepository.get_All_Company(authorUID)
    }

    fun set_Company(myCompany: MyCompany){
        executor.execute {   myCompanyRepository.inset_Company(myCompany)  }
    }

    fun set_List_Company(myCompany: MutableList<MyCompany>){
        executor.execute {  myCompanyRepository.inset_List_Company(myCompany)  }
    }

    fun delete_Company(id: String){
        myCompanyRepository.delete_Company(id)
    }
}