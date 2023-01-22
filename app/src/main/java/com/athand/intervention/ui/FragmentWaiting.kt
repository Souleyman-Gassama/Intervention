package com.athand.intervention.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.athand.intervention.R

/**
 * Cree le 11/01/2023 par Gassama Souleyman
 *
 * 1) En cas de latence il y aura une progressbar.
 * 2) Je ne veux pas que des view model du fragment par défaut sois instancie alors qu'il ne sera pas utilié.
 * Exp: Je suis déjà authentifié pas besoin de lancer FragmentLogin()
 *      - CAR, je dois être dirigé vers FragmentPersonalInfo()
 *      - CONCLUSION, les valeurs static ne seront pas instanciées ( exp: LoginOrCreateViewModel() )
 *
 *          --------------------------------------------
 *      À VERIFIER => UNE SOLUTION NATIVE A NAVIGATION COMPONENT
 *          --------------------------------------------
 * ________________________________________________________________________

 * 1) In case of latency there will be a progressbar.
 * 2) I don't want view models from the default fragment to be instantiated when it won't be used.
 * Exp: I am already authenticated no need to run FragmentLogin()
 *      - CAR, I need to be directed to FragmentPersonalInfo()
 *      - CONCLUSION, static values will not be instantiated ( exp: LoginOrCreateViewModel() )
 *          ---------------------------------------------
 *      TO BE VERIFIED => A NATIVE SOLUTION WITH COMPONENT NAVIGATION
 *          ---------------------------------------------
 *
 */

class FragmentWaiting: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_waiting, container, false)
        return view
    }
}