package com.athand.intervention.authentication.decor

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.AuthDecorator
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire

/**
 * Cree le 31/01/2022 par Gassama Souleyman
 */
class AuthWithEmailAndPasswordDecor(
    authApi: AuthWithFirebaseComponent,
    val decor: AuthWithEmailAndPasswordApi
    ) : AuthDecorator(authApi),
    AuthWithEmailAndPasswordApi {

    override fun create_With_Email_And_Password(
        viewModel: LoginDataRequire, observeAuthReponse: Observer<String>
    ) {
        decor.create_With_Email_And_Password(viewModel, observeAuthReponse)
    }

    override fun sign_In_With_Email_And_Password(
        viewModel: LoginDataRequire, observeAuthReponse: Observer<String>
    ) {
        decor.sign_In_With_Email_And_Password(viewModel, observeAuthReponse)
    }

    override fun change_Auth_Data(authData: LoginDataRequire, observeAuthReponse: Observer<String>) {
        decor.change_Auth_Data(authData, observeAuthReponse)
    }
}