package com.athand.intervention.authentication

import androidx.lifecycle.Observer


/**
 * Cree le 31/01/2022 par Gassama Souleyman
 */
abstract class AuthComponent {

    abstract fun is_Login(): Boolean

    abstract fun sign_Out(resultSigOut: Observer<String>)

    abstract fun get_User_UID(): String?

    abstract fun get_User_Auth_Name(): String?

}