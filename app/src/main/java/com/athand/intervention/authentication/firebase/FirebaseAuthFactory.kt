package com.athand.intervention.authentication.firebase

import com.athand.intervention.authentication.firebase.auth_option.FirebaseAuthWithEmailAndPassword
import com.athand.intervention.tools.AUTH_EMAIL_AND_PASSWORD
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthFactory(val firebaseAuth: FirebaseAuth) {

    var authApi: AuthWithFirebase? = null

    fun create(action: String): AuthWithFirebase {
        when (action) {
            AUTH_EMAIL_AND_PASSWORD -> {
                authApi = FirebaseAuthWithEmailAndPassword(firebaseAuth)
            }
            else -> {
                throw IllegalArgumentException("Unknown AuthWithFirebase class")
            }
        }
        return authApi!!
    }
}