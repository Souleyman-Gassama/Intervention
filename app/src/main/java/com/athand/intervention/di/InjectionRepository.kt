package com.athand.intervention.di

import android.content.Context
import com.athand.intervention.data.database.local.LocalDatabase
import com.athand.intervention.data.database.remote.adapter.CompanyRemoteAdapter
import com.athand.intervention.data.database.remote.adapter.UserRemoteAdapter
import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.data.repository.local.CompanyLocalRepository
import com.athand.intervention.data.repository.local.UserLocalRepository
import java.util.concurrent.Executors

/**
 * REMOTE ___________________________________________________
 */
fun inject_BaseRemoteRepository(): BaseRemoteRepository {
    val userRemoteAdapter = UserRemoteAdapter()
    val companyRemoteAdapter = CompanyRemoteAdapter()
    return BaseRemoteRepository(userRemoteAdapter, companyRemoteAdapter)
}

/**
 * LOCAL ___________________________________________________
 */
fun inject_BaseLocalRepository(context: Context): BaseLocalRepository {
    val userLocalRepository = inject_UserLocalRepository(context)
    val companyLocalRepository = inject_CompanyLocalRepository(context)
    val executors = Executors.newSingleThreadExecutor()
    return BaseLocalRepository(userLocalRepository, companyLocalRepository, executors)
}

fun inject_UserLocalRepository(context: Context): UserLocalRepository {
    val dataBase = LocalDatabase.get_INSTANCE(context)!!
    return UserLocalRepository(dataBase.myUserDao())
}

fun inject_CompanyLocalRepository(context: Context): CompanyLocalRepository {
    val dataBase = LocalDatabase.get_INSTANCE(context)!!
    return CompanyLocalRepository(dataBase.myCompanyDao()!!)
}
