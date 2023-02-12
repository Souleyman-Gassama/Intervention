package com.athand.intervention

import com.athand.intervention.domain.input_checking.CheckInputsStrategyFactory
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.*
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.*
import org.junit.Assert
import org.junit.Test

class InputCheckingForCompanyInfoTest {


    val badNameData: DataRequireStrategy = object : CompanyDataRequire{
        override fun get_Company_Name(): String {
            return ""
        }
    }

    val goodData: DataRequireStrategy = object : CompanyDataRequire{
        override fun get_Company_Name(): String {
            return "company name"
        }
    }

    @Test
    fun if_Can_Create_With_Good_Data(){

        val result = object: ResultsOfInputCheck{
            override fun success() {
                assert(true)
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                assert(false)
            }
        }
        CheckInputsStrategyFactory(goodData, FOR_DIALOG_CREATE_COMPANY, result).create().check_Errors()
    }

    @Test
    fun if_Can_Create_With_Bad_Company_Name(){
        val result = object: ResultsOfInputCheck{
            override fun success() {
                assert(false)
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                Assert.assertTrue(errorMap.get(KEY_COMPANY)!!)
            }
        }
        CheckInputsStrategyFactory(badNameData, FOR_DIALOG_CREATE_COMPANY, result).create().check_Errors()
    }

}
