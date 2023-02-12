package com.athand.intervention.authentication.factory

import com.athand.intervention.authentication.AuthComponent
import com.athand.intervention.tools.NO_AUTH_DECOR

/**
 * Cree le 01/02/2022 par Gassama Souleyman
 */
class AuthFactory {
    companion object {
        fun create(component: String, decor: String): AuthComponent{
            when (decor) {
                NO_AUTH_DECOR -> {
                    return AuthComponentFactory.create(component)
                }
                else -> {
                    val authComponentFactory = AuthComponentFactory.create(component)
                    val authDecorFactory = AuthDecorFactory.create(authComponentFactory, decor)
                    return authDecorFactory
                }
            }
        }
    }
}