package com.athand.intervention.authentication

import androidx.lifecycle.Observer

/**
 * Cree le 31/12/2022 par Gassama Souleyman
 */
abstract class AuthDecorator(authComponent: AuthComponent): AuthComponent() {

    private var auth: AuthComponent

    init {
        auth = authComponent
    }

    override fun is_Login(): Boolean {
        return auth.is_Login()
    }

    override fun sign_Out(resultSigOut: Observer<String>) {
        return auth.sign_Out(resultSigOut)
    }

    override fun get_User_UID(): String? {
       return auth.get_User_UID()
    }

    override fun get_User_Auth_Name(): String? {
        return auth.get_User_Auth_Name()
    }
}