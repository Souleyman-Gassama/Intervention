package com.athand.intervention.authentication.factory

import android.util.Log
import com.athand.intervention.authentication.AuthComponent
import com.athand.intervention.authentication.AuthDecorator
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.authentication.decor.FirebaseAuthWithEmailAndPasswordDecor
import com.athand.intervention.tools.AUTH_DECOR_EMAIL_AND_PASSWORD

/**
 * Cree le 31/01/2022 par Gassama Souleyman
 */
class AuthDecorFactory {
    companion object {
        var authDecorFactory: AuthDecorator? = null
    }

    fun create(authComponent: AuthComponent?, action: String): AuthDecorator {
        when (action) {
            AUTH_DECOR_EMAIL_AND_PASSWORD -> {
                Log.d("eeeee", "${authDecorFactory}")
                if (authDecorFactory is FirebaseAuthWithEmailAndPasswordDecor) {
                    return authDecorFactory!!
                }
                return FirebaseAuthWithEmailAndPasswordDecor(authComponent!! as AuthWithFirebaseComponent)
            }
            else -> {
                throw IllegalArgumentException("Unknown AuthWithFirebaseComponent class")
            }
        }
    }
}