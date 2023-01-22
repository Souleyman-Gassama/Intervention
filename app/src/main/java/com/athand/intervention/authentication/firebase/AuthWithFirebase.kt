package com.athand.intervention.authentication.firebase

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.api.AuthApi
import com.athand.intervention.tools.AUTH_EMAIL_AND_PASSWORD
import com.athand.intervention.tools.SUCCESS_SIGN_OUT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseUser

open class AuthWithFirebase(): AuthApi {

    private var currentAuthOption: String
    protected var firebaseUser: FirebaseUser? = null

    init {
        currentAuthOption = AUTH_EMAIL_AND_PASSWORD
    }

    companion object{
        private lateinit var firebaseAuth: FirebaseAuth
        var authWithFirebase: AuthApi? = null

        fun get_Instance(): AuthApi {
            if (authWithFirebase == null){
                firebaseAuth = FirebaseAuth.getInstance()
                authWithFirebase = FirebaseAuthFactory(firebaseAuth).create(AUTH_EMAIL_AND_PASSWORD)
            }
            return authWithFirebase!!
        }
    }


    fun auth_With(authOption: String) {
        if (!currentAuthOption.equals(authOption)){
            authWithFirebase = FirebaseAuthFactory(firebaseAuth).create(authOption)
        }
    }


    override fun is_Login(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun sign_Out(resultSigOut: Observer<String>) {
        firebaseAuth.signOut()
        firebaseUser = null
        resultSigOut.onChanged(SUCCESS_SIGN_OUT)
    }

    override fun get_User_UID(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun get_User_Auth_Name(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun get_Firebase_User(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}