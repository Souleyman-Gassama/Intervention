package com.athand.intervention.domain.auth_use_case

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.decor.AuthWithEmailAndPasswordDecor
import com.athand.intervention.authentication.factory.AuthFactory
import com.athand.intervention.domain.input_checking.CheckInputsStrategyFactory
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.*

/**
 * Auteur Gassama Souleyman
 */
class LoginByEmailAndPasswordWithFirebase(
    var data: DataRequireStrategy,
    var resultLogin: (AuthOrCreationResult) -> Unit) {
    var authApi: AuthWithEmailAndPasswordDecor

    init {
        authApi = AuthFactory.create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
//        authApi = AuthFactory().create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
                as AuthWithEmailAndPasswordDecor
        execute()
    }

    fun execute() {
        val resultInputLogIn = get_Configuration_Of_Input_Verification_Result()
        val strategy =
            CheckInputsStrategyFactory(data, FOR_LOGIN, resultInputLogIn).create()
        CheckValidityOfInputsContext(strategy)
                .check_If_Data_Is_Valid()
    }

    /**
     * OBSERVER INPUT RESULT ___________________________________________
     */
    private fun get_Configuration_Of_Input_Verification_Result(): ResultsOfInputCheck {
        return object : ResultsOfInputCheck {
            override fun success() {
                val observeAuthReponse = get_Authentication_Result_Configuration()
                authApi.sign_In_With_Email_And_Password(
                (data as LoginDataRequire), observeAuthReponse )
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                val message = ERROR_INPUT
                val result = AuthOrCreationResult(false, message, errorMap)
                resultLogin(result)
            }
        }
    }


    /**
     * OBSERVER LOGIN USER RESULT ___________________________________________
     */
    private fun get_Authentication_Result_Configuration(): Observer<String> {
        return Observer<String> { reponse ->
            var isSuccess = false
            if( reponse.equals(SUCCESS_LOGIN) ) {
                isSuccess = true
            }
            val result = AuthOrCreationResult(isSuccess, reponse, mutableMapOf())
            resultLogin(result)
        }
    }

}