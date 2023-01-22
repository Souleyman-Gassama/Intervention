package com.athand.intervention.domain

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * Cree le 20/01/2023 par Gassama Souleyman
 */
class CustumLifeCycleOwner: LifecycleOwner {

    private val lifecycleRegister: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    init {
        start()
    }

    fun start(){
        lifecycleRegister.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun stop(){
        lifecycleRegister.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun desroy(){
        lifecycleRegister.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegister
    }

}
