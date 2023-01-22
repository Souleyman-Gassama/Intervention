package com.athand.intervention.domain.input_checking.concrete_strategys

import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.*
import com.athand.intervention.tools.KEY_FIRST_NAME
import com.athand.intervention.tools.KEY_LAST_NAME

/**
 * Cree le 29/12/2022 par Gassama Souleyman
 */
class CheckInputsForCreateAccountInput(
    var data: DataRequireStrategy,
    val result: ResultsOfInputCheck
) :
    InputErrorChecked {

    private var emailString: String? = ""
    private var passwordString: String? = ""
    private var firstNameString: String? = ""
    private var lastNameString: String? = ""

    private var lastNameError: Boolean = false
    private var firstNameError: Boolean = false

    private var checkLoginEntries: CheckInputsForLoginInput

    init {
        checkLoginEntries = CheckInputsForLoginInput(data as LoginDataRequire, null)
    }

    private fun get_Data_From_View_Model() {
        firstNameString = (data as ProfileDataRequire).get_Firste_Name()
        lastNameString = (data as ProfileDataRequire).get_Last_Name()
        emailString = (data as LoginDataRequire).get_Email()
        passwordString = (data as LoginDataRequire).get_Password()
    }

    private fun check_If_Can_Create_Profile_Data(): Boolean {
        if (lastNameString.contentEquals("")) {
            lastNameError = true
        }
        if (firstNameString.contentEquals("")) {
            firstNameError = true
        }
        val errorLoginEntries = checkLoginEntries.check_Errors()
        return !lastNameError && !firstNameError && errorLoginEntries
    }


    override fun check_Errors(): Boolean {
        get_Data_From_View_Model()
        val canCreate = check_If_Can_Create_Profile_Data()
        if (canCreate) {
            result.success()
        } else {
            result.failure(get_Error_Map())
        }
        return canCreate
    }

    override fun get_Error_Map(): MutableMap<String, Boolean> {
        return mutableMapOf(
            Pair(KEY_FIRST_NAME, firstNameError),
            Pair(KEY_LAST_NAME, lastNameError)
        )
            .apply { putAll(checkLoginEntries.get_Error_Map()) }
    }

}