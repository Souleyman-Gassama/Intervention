package com.athand.intervention.domain.input_checking.concrete_strategys

import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.DataRequireStrategy.CompanyDataRequire
import com.athand.intervention.tools.KEY_COMPANY
import com.athand.intervention.tools.KEY_EMAIL

//import com.athand.intervention.domain.data_require.CompanyDataRequire

/**
 * Cree le 15/01/2023 par Gassama Souleyman
 */
class CheckInputsForCreateCompanyInput(val dataRequire: CompanyDataRequire, val result: ResultsOfInputCheck) :
    InputErrorChecked {

    private var companyNameString: String? = ""
    private var companyNameError: Boolean = false

    private fun get_Data_From_View_Model() {
        companyNameString = dataRequire.get_Company_Name()
    }

    private fun check_If_Can_Create_Company(): Boolean {
        if (companyNameString.contentEquals("")) {
            companyNameError = true
        }
        return !companyNameError
    }

    override fun check_Errors(): Boolean {
        get_Data_From_View_Model()
        val canCreate = check_If_Can_Create_Company()
        if (canCreate) {
            result.success()
        } else {
            result.failure(get_Error_Map())
        }
        return canCreate
    }

    override fun get_Error_Map(): MutableMap<String, Boolean> {
        return mutableMapOf(Pair(KEY_COMPANY, companyNameError))
    }
}