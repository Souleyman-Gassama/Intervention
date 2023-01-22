package com.athand.intervention.tools

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
/**
 * Cree le 28/12/2022 par Gassama Souleyman
 */
fun get_Nav_HostFragment(appCompat: AppCompatActivity, host: Int): NavHostFragment{
    return appCompat.supportFragmentManager.findFragmentById(host) as NavHostFragment
}
fun get_Nav_Controller(appCompat: AppCompatActivity, host: Int): NavController{
    val navHostFragment = appCompat.supportFragmentManager.findFragmentById(host) as NavHostFragment
    return navHostFragment.navController
}

fun get_Current_Fragment(appCompat: AppCompatActivity, host: Int): Fragment {
    val navHostFragment =
        appCompat.supportFragmentManager.findFragmentById(host) as NavHostFragment
    return navHostFragment.childFragmentManager.fragments[0]
}