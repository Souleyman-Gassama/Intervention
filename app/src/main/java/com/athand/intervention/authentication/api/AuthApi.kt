package com.athand.intervention.authentication.api

import androidx.lifecycle.Observer


/**
 * Cree le 29/12/2022 par Gassama Souleyman
 */
interface AuthApi {

    fun is_Login(): Boolean

    fun sign_Out(resultSigOut: Observer<String>)

    fun get_User_UID(): String?

    fun get_User_Auth_Name(): String?

}