package com.athand.intervention

import com.athand.intervention.domain.input_checking.CheckInputsStrategyFactory
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext.InputErrorChecked
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.*
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.*
import org.junit.Assert
import org.junit.Test

class InputCheckingForProfilUserTest {


    val badFirstNameData: DataRequireStrategy = object : ProfileDataRequire{

        override fun get_Firste_Name(): String {
            return ""
        }

        override fun get_Last_Name(): String {
            return "name"
        }

        override fun get_Email(): String {
            return "aa@aa.aa"
        }
    }

    val badLasetNameData: DataRequireStrategy = object : ProfileDataRequire{

        override fun get_Firste_Name(): String {
            return "name"
        }

        override fun get_Last_Name(): String {
            return ""
        }

        override fun get_Email(): String {
            return "aa@aa.aa"
        }

    }

    val badEmailData: DataRequireStrategy = object : ProfileDataRequire{

        override fun get_Firste_Name(): String {
            return "name"
        }

        override fun get_Last_Name(): String {
            return "name"
        }

        override fun get_Email(): String {
            return ""
        }
    }

    val goodData: DataRequireStrategy = object : ProfileDataRequire{
        override fun get_Firste_Name(): String {
            return "name"
        }

        override fun get_Last_Name(): String {
            return "name"
        }
        override fun get_Email(): String {
            return "aaa@aaa.aaa"
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
        CheckInputsStrategyFactory(goodData, FOR_PERSONAL_INFO, result).create().check_Errors()
    }

    @Test
    fun if_Can_Create_With_Bad_First_Name(){
        val result = object: ResultsOfInputCheck{
            override fun success() {
                assert(false)
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                Assert.assertTrue(errorMap.get(KEY_FIRST_NAME)!!)
            }
        }
        CheckInputsStrategyFactory(badFirstNameData, FOR_PERSONAL_INFO, result).create().check_Errors()
    }

    @Test
    fun if_Can_Create_With_Bad_Last_Name(){
        val result = object: ResultsOfInputCheck{
            override fun success() {
                assert(false)
            }

            override fun failure(errorMap: MutableMap<String, Boolean>) {
                Assert.assertTrue(errorMap.get(KEY_LAST_NAME)!!)
            }
        }
        CheckInputsStrategyFactory(badLasetNameData, FOR_PERSONAL_INFO, result).create().check_Errors()
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
        CheckInputsStrategyFactory(badEmailData, FOR_PERSONAL_INFO, result).create().check_Errors()
    }

}
