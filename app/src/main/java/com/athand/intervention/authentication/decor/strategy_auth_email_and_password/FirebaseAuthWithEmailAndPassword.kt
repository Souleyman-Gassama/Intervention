package com.athand.intervention.authentication.decor.strategy_auth_email_and_password

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.authentication.decor.AuthWithEmailAndPasswordApi
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.tools.*
import com.google.firebase.auth.FirebaseAuth

/**
 * Cree le 31/12/2022 par Gassama Souleyman
 */
class FirebaseAuthWithEmailAndPassword(authApi: AuthWithFirebaseComponent) :
    AuthWithEmailAndPasswordApi {

    var auth: AuthWithFirebaseComponent
    var firebaseAuth: FirebaseAuth

    init {
        auth = authApi
        firebaseAuth = authApi.firebaseAuth
    }

    private var email: String = ""
    private var password: String = ""

    private fun get_Data(viewModel: LoginDataRequire) {
        email = viewModel.get_Email()
        password = viewModel.get_Password()
    }

    override fun sign_In_With_Email_And_Password(
        viewModel: LoginDataRequire,
        observeAuthReponse: Observer<String>
    ) {
        get_Data(viewModel)
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.set_Firebase_User(it.user)
                observeAuthReponse.onChanged(SUCCESS_LOGIN)
            }
            .addOnFailureListener {
                observeAuthReponse.onChanged(FAILURE_LOGIN)
            }

    }

    override fun create_With_Email_And_Password(
        viewModel: LoginDataRequire,
        observeAuthReponse: Observer<String>
    ) {
        get_Data(viewModel)
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.set_Firebase_User(it.user)
                observeAuthReponse.onChanged(SUCCESS_CREATE)
            }

            .addOnFailureListener {
                observeAuthReponse.onChanged(it.message)
            }
    }


    override fun change_Auth_Data(authData: LoginDataRequire, observeAuthReponse: Observer<String>) {
        firebaseAuth.setLanguageCode("fr")

        firebaseAuth.currentUser?.updatePassword(authData.get_Password())
            ?.addOnSuccessListener {
                firebaseAuth.currentUser?.updateEmail(authData.get_Email())
                    ?.addOnSuccessListener {
                        observeAuthReponse.onChanged("success update Login data")
                    }
                    ?.addOnFailureListener {
                        observeAuthReponse.onChanged(it.message)
                    }
            }
            ?.addOnFailureListener {
                observeAuthReponse.onChanged(it.message)
            }

    }
}