package com.athand.intervention.authentication.decor

import androidx.lifecycle.Observer
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire

/**
 * Cree le 29/12/2022 par Gassama Souleyman
 */
interface AuthWithEmailAndPasswordApi {

    fun create_With_Email_And_Password
                (viewModel: LoginDataRequire, observeAuthReponse: Observer<String>)

    fun sign_In_With_Email_And_Password
                (viewModel: LoginDataRequire, observeAuthReponse: Observer<String>)

    fun change_Auth_Data(authData: LoginDataRequire, observeAuthReponse: Observer<String>)
}