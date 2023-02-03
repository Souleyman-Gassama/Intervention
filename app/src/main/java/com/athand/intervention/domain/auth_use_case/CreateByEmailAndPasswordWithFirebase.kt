package com.athand.intervention.domain.auth

import androidx.lifecycle.Observer
import com.athand.intervention.authentication.decor.FirebaseAuthWithEmailAndPasswordDecor
import com.athand.intervention.authentication.factory.AuthFactory
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.*
import com.google.firebase.auth.AuthResult

/**
 * Auteur Gassama Souleyman
 */
class CreateByEmailAndPasswordWithFirebase(
    var data: DataRequireStrategy,
    var resultCreate: (AuthOrCreationResult) -> Unit
) {

    var authApi: FirebaseAuthWithEmailAndPasswordDecor

    init {
        authApi = AuthFactory().create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
                as FirebaseAuthWithEmailAndPasswordDecor
        execute()
    }

    fun execute() {
        var resultInputCreate = get_Configuration_Of_Input_Verification_Result()
        CheckValidityOfInputsContext(data)
            .check_If_Data_Is_Valid( FOR_CREATE, resultInputCreate )
    }

/**
 * OBSERVER INPUT RESULT ___________________________________________
 */
    private fun get_Configuration_Of_Input_Verification_Result(): ResultsOfInputCheck {
        return object : ResultsOfInputCheck {
            override fun success() {
                val observeAuthReponse = get_Authentication_Result_Configuration()
                authApi.create_With_Email_And_Password(
                    (data as LoginDataRequire), observeAuthReponse
                )
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                val message = ERROR_INPUT
                val result = AuthOrCreationResult(false, message, errorMap)
                resultCreate(result)
            }
        }
    }

/**
* OBSERVER CREATE USER RESULT ___________________________________________
*/
    private fun get_Authentication_Result_Configuration(): Observer<String> {
        return Observer<String> { reponse ->
            var isSuccess = false
            if( reponse.equals(SUCCESS_LOGIN) ) {
                isSuccess = true
            }

            val result = AuthOrCreationResult(isSuccess, reponse, mutableMapOf())
            resultCreate(result)
        }
    }

}