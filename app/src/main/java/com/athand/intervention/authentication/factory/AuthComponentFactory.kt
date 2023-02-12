package com.athand.intervention.authentication.factory

import android.util.Log
import com.athand.intervention.authentication.AuthComponent
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.tools.FIREBASE_AUTH_COMPONENT

class AuthComponentFactory {

    companion object {
        var authComponentFactory: AuthComponent? = null

        fun create(component: String): AuthComponent {
            when (component) {
                FIREBASE_AUTH_COMPONENT -> {
                    if ( !(authComponentFactory is AuthWithFirebaseComponent) ) {
                        authComponentFactory = AuthWithFirebaseComponent()
                    }
                    return authComponentFactory!!
                }
                else -> {
                    throw IllegalArgumentException("Unknown AuthWithFirebaseComponent class")
                }
            }
        }
    }
}