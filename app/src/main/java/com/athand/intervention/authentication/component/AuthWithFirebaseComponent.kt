package com.athand.intervention.authentication.component

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.AuthComponent
import com.athand.intervention.tools.NO_AUTH_DECOR
import com.athand.intervention.tools.SUCCESS_SIGN_OUT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
/**
 * Cree le 31/01/2022 par Gassama Souleyman
 */
open class AuthWithFirebaseComponent() : AuthComponent() {

    private var currentAuthOption: String
    protected var firebaseUser: FirebaseUser? = null
    var firebaseAuth: FirebaseAuth

    init {
        currentAuthOption = NO_AUTH_DECOR
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun is_Login(): Boolean {
        get_Firebase_User()
        return firebaseUser != null
    }

    override fun sign_Out(resultSigOut: Observer<String>) {
        firebaseAuth.signOut()
        firebaseUser = null
        resultSigOut.onChanged(SUCCESS_SIGN_OUT)
    }

    override fun get_User_UID(): String? {
        get_Firebase_User()
        return firebaseUser?.uid
    }

    override fun get_User_Auth_Name(): String? {
        get_Firebase_User()
        return firebaseUser?.email
    }

    fun set_Firebase_User(currentUser: FirebaseUser?) {
        firebaseUser = currentUser
    }

    fun get_Firebase_User(): FirebaseUser? {
        if (firebaseUser == null) {
            firebaseUser = firebaseAuth.currentUser
        }
        return firebaseUser
    }
}