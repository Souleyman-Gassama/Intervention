package com.athand.intervention.domain.auth

import com.athand.intervention.authentication.api.AuthApi
import com.athand.intervention.authentication.api.AuthWithEmailAndPasswordApi
import com.athand.intervention.authentication.firebase.AuthWithFirebase
import com.athand.intervention.authentication.firebase.auth_option.FirebaseAuthWithEmailAndPassword
import com.athand.intervention.domain.input_checking.DataRequireStrategy.EmailDataRequire

class UpdateFirebaseAuthData(private val loginDataRequire: EmailDataRequire) {

    val authWithFirebase: AuthApi

    init {
        authWithFirebase = AuthWithFirebase.get_Instance()

        //NOTE: AuthWithFirebase implement AuthApi
        (authWithFirebase as FirebaseAuthWithEmailAndPassword).change_Email(loginDataRequire.get_Email())
    }


}