package com.athand.intervention.domain.input_checking
/**
 * Cree le 29/12/2022 par Gassama Souleyman

 * Class Context du Pattern Strategy
 */
class CheckValidityOfInputsContext(val inputErrorCheckedStrategy: InputErrorChecked) {

    fun check_If_Data_Is_Valid() {
        inputErrorCheckedStrategy.check_Errors()
    }

    interface InputErrorChecked {
        fun get_Error_Map(): MutableMap<String, Boolean>
        fun check_Errors(): Boolean
    }
}