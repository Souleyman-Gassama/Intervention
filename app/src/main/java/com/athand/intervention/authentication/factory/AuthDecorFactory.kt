package com.athand.intervention.authentication.factory

import android.util.Log
import com.athand.intervention.authentication.AuthComponent
import com.athand.intervention.authentication.AuthDecorator
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.authentication.decor.AuthWithEmailAndPasswordDecor
import com.athand.intervention.authentication.decor.strategy_auth_email_and_password.FirebaseAuthWithEmailAndPassword
import com.athand.intervention.tools.AUTH_DECOR_EMAIL_AND_PASSWORD

/**
 * Cree le 31/01/2022 par Gassama Souleyman
 */
class AuthDecorFactory {
    companion object {
        var authDecorFactory: AuthDecorator? = null

        fun create(authComponent: AuthComponent?, action: String): AuthDecorator {
            when (action) {
                AUTH_DECOR_EMAIL_AND_PASSWORD -> {
                    if (!(authDecorFactory is AuthWithEmailAndPasswordDecor)) {
                        val startegy =
                            FirebaseAuthWithEmailAndPassword(authComponent!! as AuthWithFirebaseComponent)
                        authDecorFactory =
                            AuthWithEmailAndPasswordDecor(authComponent as AuthWithFirebaseComponent, startegy)
                    }
                    return authDecorFactory!!
                }
                else -> {
                    throw IllegalArgumentException("Unknown AuthWithFirebaseComponent class")
                }
            }
        }
    }
}