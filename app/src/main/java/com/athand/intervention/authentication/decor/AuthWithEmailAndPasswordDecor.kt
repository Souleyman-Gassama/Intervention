package com.athand.intervention.authentication.decor

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.AuthDecorator
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.authentication.decor.api.AuthWithEmailAndPasswordApi
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire

class AuthWithEmailAndPassword(
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

    override fun change_Email(email: String) {
        decor.change_Email(email)
    }

    override fun change_Password(dataForReset: String) {
        decor.change_Password(dataForReset)
    }
}