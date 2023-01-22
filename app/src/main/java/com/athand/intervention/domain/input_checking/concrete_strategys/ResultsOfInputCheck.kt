package com.athand.intervention.domain.input_checking.concrete_strategys
/**
 * Cree le 15/01/2023 par Gassama Souleyman
 */
interface ResultsOfInputCheck {
    fun success()
    fun failure(errorMap: MutableMap<String, Boolean>)
}