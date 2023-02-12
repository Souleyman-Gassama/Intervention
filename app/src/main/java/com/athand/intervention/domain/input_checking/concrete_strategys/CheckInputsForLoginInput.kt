package com.athand.intervention.domain.input_checking.concrete_strategys

import android.util.Log
import android.util.Patterns
import androidx.core.util.PatternsCompat
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.tools.KEY_EMAIL
import com.athand.intervention.tools.KEY_PASSWORD

//import com.athand.intervention.domain.data_require.ProfileDataRequireForLoginOrCreate

/**
 * Cree le 29/12/2022 par Gassama Souleyman
 */
class CheckInputsForLoginInput(
    val dataRequire: LoginDataRequire,
    val result: ResultsOfInputCheck?
) :
    InputErrorChecked {
    private var emailString: String? = ""
    private var passwordString: String? = ""

    private var emailError: Boolean = false
    private var passwordError: Boolean = false


    private fun get_Data_From_View_Model() {
        emailString = dataRequire.get_Email()
        passwordString = dataRequire.get_Password()
    }


    fun check_If_Can_Login(): Boolean {
        if (passwordString.contentEquals("") || passwordString?.length!! < 6) {
            passwordError = true
        }

        if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailString).matches()) {
            emailError = true
        }
        return !passwordError && !emailError
    }


    override fun check_Errors(): Boolean {
        get_Data_From_View_Model()
        val canLogin = check_If_Can_Login()
        // note: result != null car CheckForCreateAccount utilise CheckForLogin et ne fourni pas de callback
        if (result != null) {
            if (canLogin) {
                result.success()
            } else {
                result.failure(get_Error_Map())
            }
        }
        return canLogin
    }

    override fun get_Error_Map(): MutableMap<String, Boolean> {
        return mutableMapOf(
            Pair(KEY_EMAIL, emailError),
            Pair(KEY_PASSWORD, passwordError)
        )
    }

}