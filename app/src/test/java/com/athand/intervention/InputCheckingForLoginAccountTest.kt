package com.athand.intervention

import com.athand.intervention.domain.input_checking.CheckInputsStrategyFactory
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.DataRequireStrategy.ProfileDataRequire
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.*
import org.junit.Assert
import org.junit.Test

class InputCheckingForLoginAccountTest {

    val badEmailData: DataRequireStrategy = object : LoginDataRequire{
        override fun get_Email(): String {
            return ""
        }

        override fun get_Password(): String {
            return "aaaaaa"
        }
    }

    val badPasswordData: DataRequireStrategy = object : LoginDataRequire{
        override fun get_Email(): String {
            return "aa@aa.aa"
        }

        override fun get_Password(): String {
            return ""
        }
    }

    val goodData: DataRequireStrategy = object : LoginDataRequire{
        override fun get_Email(): String {
            return "aaa@aaa.aaa"
        }

        override fun get_Password(): String {
            return "aaaaaa"
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
        CheckInputsStrategyFactory(goodData, FOR_LOGIN, result).create().check_Errors()
    }

    @Test
    fun if_Can_Create_With_Bad_Email(){
        val result = object: ResultsOfInputCheck{
            override fun success() {
                assert(false)
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                Assert.assertTrue(errorMap.get(KEY_EMAIL)!!)
            }
        }
        CheckInputsStrategyFactory(badEmailData, FOR_LOGIN, result).create().check_Errors()
    }

    @Test
    fun if_Can_Create_With_Bad_Password(){
        val result = object: ResultsOfInputCheck{
            override fun success() {
                assert(false)
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                Assert.assertTrue(errorMap.get(KEY_PASSWORD)!!)
            }
        }
        CheckInputsStrategyFactory(badPasswordData, FOR_LOGIN, result).create().check_Errors()
    }
}
