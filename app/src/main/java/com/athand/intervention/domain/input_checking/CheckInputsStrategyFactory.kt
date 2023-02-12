package com.athand.intervention.domain.input_checking

import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.concrete_strategys.*
import com.athand.intervention.tools.FOR_CREATE
import com.athand.intervention.tools.FOR_DIALOG_CREATE_COMPANY
import com.athand.intervention.tools.FOR_LOGIN
import com.athand.intervention.tools.FOR_PERSONAL_INFO
/**
 * Cree le 05/02/2023 par Gassama Souleyman

 * Pattern Abstract Factory
 */
class CheckInputsStrategyFactory(var data: DataRequireStrategy, val viewCurrentlyDisplayed: String, val result: ResultsOfInputCheck) {

    fun create(): InputErrorChecked {
        when (viewCurrentlyDisplayed) {
            FOR_CREATE -> {
                return CheckInputsForCreateAccountInput(data, result)
            }
            FOR_PERSONAL_INFO -> {
                return CheckInputsForUpdateAccountInput(data, result)
            }
            FOR_LOGIN -> {
                return CheckInputsForLoginInput(data as DataRequireStrategy.LoginDataRequire, result)
            }
            FOR_DIALOG_CREATE_COMPANY -> {
                return CheckInputsForCreateCompanyInput(
                    data as DataRequireStrategy.CompanyDataRequire,
                    result
                )
            }
            else -> { throw IllegalArgumentException("Unknown ViewModel class")}
        }
    }
}