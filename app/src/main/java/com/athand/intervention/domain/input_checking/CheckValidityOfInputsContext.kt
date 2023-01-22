package com.athand.intervention.domain.input_checking

import com.athand.intervention.domain.input_checking.DataRequireStrategy.CompanyDataRequire
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.concrete_strategys.*
import com.athand.intervention.tools.FOR_CREATE
import com.athand.intervention.tools.FOR_DIALOG_CREATE_COMPANY
import com.athand.intervention.tools.FOR_LOGIN
import com.athand.intervention.tools.FOR_PERSONAL_INFO

/**
 * Cree le 29/12/2022 par Gassama Souleyman

 * Class Context du Pattern Strategy
 */
class CheckValidityOfInputsContext(var data: DataRequireStrategy) {

    private lateinit var errorEntries: InputErrorChecked

    fun check_If_Data_Is_Valid(viewCurrentlyDisplayed: String, result: ResultsOfInputCheck) {
        when (viewCurrentlyDisplayed) {
            FOR_CREATE -> {
                errorEntries = CheckInputsForCreateAccountInput(data, result)
            }
            FOR_PERSONAL_INFO -> {
                errorEntries = CheckInputsForUpdateAccountInput(data, result)
            }
            FOR_LOGIN -> {
                errorEntries = CheckInputsForLoginInput(data as LoginDataRequire, result)
            }
            FOR_DIALOG_CREATE_COMPANY -> {
                errorEntries = CheckInputsForCreateCompanyInput(data as CompanyDataRequire, result)
            }
        }
        errorEntries.check_Errors()
    }

    interface InputErrorChecked {
        fun get_Error_Map(): MutableMap<String, Boolean>
        fun check_Errors(): Boolean
    }
}