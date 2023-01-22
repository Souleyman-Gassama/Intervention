package com.athand.intervention.domain.auth

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.firebase.AuthWithFirebase
import com.athand.intervention.authentication.firebase.auth_option.FirebaseAuthWithEmailAndPassword
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.ERROR_INPUT
import com.athand.intervention.tools.FOR_LOGIN

class LoginByEmailAndPasswordWithFirebase(
    var data: DataRequireStrategy,
    var resultLogin: (AuthOrCreationResult) -> Unit) {

    var authApi: FirebaseAuthWithEmailAndPassword

    init {
        authApi = AuthWithFirebase.get_Instance() as FirebaseAuthWithEmailAndPassword
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