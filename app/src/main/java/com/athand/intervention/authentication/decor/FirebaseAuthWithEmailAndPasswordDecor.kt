package com.athand.intervention.authentication.decor

import android.util.Log
import androidx.lifecycle.Observer
import com.athand.intervention.authentication.AuthDecorator
import com.athand.intervention.authentication.decor.api.AuthWithEmailAndPasswordApi
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.tools.*
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.absoluteValue

/**
 * Cree le 31/01/2022 par Gassama Souleyman
 */
class FirebaseAuthWithEmailAndPasswordDecor(authApi: AuthWithFirebaseComponent): AuthDecorator(authApi),
    AuthWithEmailAndPasswordApi {

    var auth: AuthWithFirebaseComponent
    var firebaseAuth: FirebaseAuth

    init {
        auth = authApi
        firebaseAuth = authApi.firebaseAuth
    }

    private var email: String = ""
    private var password: String = ""

    private fun get_Data(viewModel: LoginDataRequire){
        email = viewModel.get_Email()
        password = viewModel.get_Password()
    }

    override fun sign_In_With_Email_And_Password
                (viewModel: LoginDataRequire, observeAuthReponse: Observer<String>) {
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

    override fun create_With_Email_And_Password
                (viewModel: LoginDataRequire, observeAuthReponse: Observer<String>) {
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


    override fun change_Email(email: String) {
        auth.get_Firebase_User()?.updateEmail(email)
            ?.addOnSuccessListener {

            }
    }

    override fun change_Password(emailString: String) {
        firebaseAuth.sendPasswordResetEmail(emailString)
    }
}