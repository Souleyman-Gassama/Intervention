package com.athand.intervention.ui.intervention_slip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.athand.intervention.R
import com.athand.intervention.tools.display_Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentInterventionReportList: Fragment(), OnClickListener {

    private lateinit var buttonCreateIntervention: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intervention_report_list, container, false)
        configuration_View(view)
        configuration_Listener()
        return view
    }

    private fun configuration_View(view: View?) {
        buttonCreateIntervention = view?.findViewById(R.id.button_create_intervention)!!
    }

    private fun configuration_Listener() {
        buttonCreateIntervention.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            buttonCreateIntervention -> {
                display_Toast(requireContext(), "add")
            }
        }
    }


}