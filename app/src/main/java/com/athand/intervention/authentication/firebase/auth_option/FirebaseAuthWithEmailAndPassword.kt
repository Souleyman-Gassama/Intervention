package com.athand.intervention.authentication.firebase.auth_option

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.api.AuthWithEmailAndPasswordApi
import com.athand.intervention.authentication.firebase.AuthWithFirebase
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.tools.*
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthWithEmailAndPassword(val firebaseAuth: FirebaseAuth): AuthWithFirebase(),
    AuthWithEmailAndPasswordApi {

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
                firebaseUser = it.user
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
                firebaseUser = it.user
                observeAuthReponse.onChanged(SUCCESS_CREATE)
            }

            .addOnFailureListener {
                observeAuthReponse.onChanged(FAILURE_CREATE)
            }
    }


    override fun change_Email(email: String) {
        firebaseUser?.updateEmail(email)
            ?.addOnSuccessListener {

            }
    }

    override fun change_Password(emailString: String) {
        firebaseAuth.sendPasswordResetEmail(emailString)
    }
}