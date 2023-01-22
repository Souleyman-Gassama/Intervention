package com.athand.intervention.domain.input_checking.concrete_strategys

import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.DataRequireStrategy.ProfileDataRequire
import com.athand.intervention.tools.KEY_EMAIL
import com.athand.intervention.tools.KEY_FIRST_NAME
import com.athand.intervention.tools.KEY_LAST_NAME

//import com.athand.intervention.domain.data_require.ProfileDataRequire

/**
 * Cree le 29/12/2022 par Gassama Souleyman
 */
class CheckInputsForUpdateAccountInput(val data: DataRequireStrategy, val result: ResultsOfInputCheck):
    InputErrorChecked {

    private var emailString: String? = ""
    private var firstNameString: String? = ""
    private var lastNameString: String? = ""

    private var lastNameError: Boolean = false
    private var firstNameError: Boolean = false
    private var emailError: Boolean = false

    private fun get_Data_From_View_Model() {
        if (data is ProfileDataRequire) {
            firstNameString = data.get_Firste_Name()
            lastNameString = data.get_Last_Name()
            emailString = data.get_Email()
        }
    }

    private fun check_If_Can_Update_Profile_Data(): Boolean {
        if (lastNameString.contentEquals("")) {
            lastNameError = true
        }
        if (firstNameString.contentEquals("")) {
            firstNameError = true
        }

        if (emailString.contentEquals("")) {
            emailError = true
        }
        return !lastNameError && !firstNameError && !emailError
    }

    override fun check_Errors(): Boolean{
        get_Data_From_View_Model()
        val canLogin = check_If_Can_Update_Profile_Data()
        if (canLogin) {
            result.success()
        }else{
            result.failure(get_Error_Map())
        }
        return canLogin
    }

    override fun get_Error_Map(): MutableMap<String, Boolean> {
        return mutableMapOf(
            Pair(KEY_EMAIL, emailError),
            Pair(KEY_FIRST_NAME, firstNameError),
            Pair(KEY_LAST_NAME, lastNameError)
        )
    }


}