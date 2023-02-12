package com.athand.intervention.ui.intervention_slip.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.athand.intervention.R

class FragmentInterventionReportDetail: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intervention_report_details, container, false)
        configuration_View(view)
        return view
    }

    private fun configuration_View(view: View?) {

    }
}