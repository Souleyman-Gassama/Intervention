package com.athand.intervention.domain.auth

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.component.AuthWithFirebaseComponent
import com.athand.intervention.authentication.decor.FirebaseAuthWithEmailAndPasswordDecor
import com.athand.intervention.authentication.factory.AuthFactory
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.AUTH_DECOR_EMAIL_AND_PASSWORD
import com.athand.intervention.tools.ERROR_INPUT
import com.athand.intervention.tools.FIREBASE_AUTH_COMPONENT
import com.athand.intervention.tools.FOR_LOGIN

class LoginByEmailAndPasswordWithFirebase(
    var data: DataRequireStrategy,
    var resultLogin: (AuthOrCreationResult) -> Unit) {

    var authApi: FirebaseAuthWithEmailAndPasswordDecor

    init {
        authApi = AuthFactory().create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
                as FirebaseAuthWithEmailAndPasswordDecor
        execute()
    }

    fun execute() {
        val resultInputLogIn = get_Configuration_Of_Input_Verification_Result()
        CheckValidityOfInputsContext(data)
            .check_If_Data_Is_Valid(FOR_LOGIN, resultInputLogIn)
    }

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

    private fun get_Authentication_Result_Configuration(): Observer<String> {
        return Observer<String> { reponse ->
            val message = reponse
            val result = AuthOrCreationResult(true, message, mutableMapOf())
            resultLogin(result)
        }
    }

}