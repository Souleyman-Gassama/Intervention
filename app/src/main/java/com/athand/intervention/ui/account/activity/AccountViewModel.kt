package com.athand.intervention.ui.account.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Cree le 14/01/2023 par Gassama Souleyman
 */
class AccountViewModel: ViewModel() {
    var currentDestination: MutableLiveData<Int>

    init {
        currentDestination = MutableLiveData(0)
    }


    fun get_Destination_Fragment_Id(): MutableLiveData<Int>{
        return currentDestination
    }

    fun set_Destination_Fragment_Id(destination: Int){
        currentDestination.value = destination
    }
}