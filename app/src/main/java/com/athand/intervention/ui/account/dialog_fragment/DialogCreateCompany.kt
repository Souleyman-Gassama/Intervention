package com.athand.intervention.ui.account.dialog_fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.athand.intervention.R
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.di.FactoryAccountViewModel
import com.athand.intervention.tools.ARG_BUNDLE_COMPANY
import com.athand.intervention.tools.ARG_BUNDLE_USER
import com.athand.intervention.tools.CREATE_COMPANY
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class DialogCreateCompany : DialogFragment(), View.OnClickListener {

    private var user: MyUser? = null
    private var company: MyCompany? = null

    val dialogCreateCompanyViewModel: DialogCreateCompanyViewModel by viewModels {
        FactoryAccountViewModel.get_Factory(CREATE_COMPANY)
    }

    private var companyName: TextInputLayout? = null
    private var companyAddress: TextInputLayout? = null
    private var companyEmail: TextInputLayout? = null
    private var companyphone: TextInputLayout? = null

    private var buttonCreateCompany: MaterialButton? = null


    /**
     * CREATE ALERT DIALOGUE _____________________________________________________________________________
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

// CREATE VIEW WITCH INFLATER AND SET IN BUILDER --------------------------------------------
// -------
        val alertBuilder: AlertDialog.Builder
        alertBuilder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = requireActivity().getLayoutInflater()
        val view: View
        view = inflater.inflate(R.layout.dialog_create_company, null)
        alertBuilder.setView(view)
        configure_View(view)
        configure_Listener()
        configure_Listener_Edit_Text()
        get_Data_From_Bundle()
        set_Data_To_View()
        display_Error_To_View()
        dialog_Close_Listener_Configuration()

// CREATE DIALOG -----------------------------------------------------------------------------------
        val dialog = alertBuilder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }


    /**
     * CONFIGURATION VIEW ___________________________________________
     */
    private fun configure_View(view: View) {
        companyName = view.findViewById(R.id.company_name_create_company_dialog)
        companyAddress = view.findViewById(R.id.company_address_create_company_dialog)
        companyEmail = view.findViewById(R.id.company_email_create_company_dialog)
        companyphone = view.findViewById(R.id.company_phone_create_company_dialog)
        buttonCreateCompany = view.findViewById(R.id.button_save_create_company_dialog)

    }


    /**
     * CONFIGURATION LISTENER BUTTON ___________________________________________
     */
    private fun configure_Listener() {
        buttonCreateCompany?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonCreateCompany -> {
                dialogCreateCompanyViewModel?.click_Create_Company()
            }
        }
    }


    /**
     * CONFIGURATION LISTENER EDIT TEXT___________________________________________
     */
    private fun configure_Listener_Edit_Text() {
        companyName?.editText?.addTextChangedListener { newString ->
            dialogCreateCompanyViewModel?.set_Company_Name(newString.toString())
            companyName?.error = ""
        }

        companyAddress?.editText?.addTextChangedListener { newString ->
            dialogCreateCompanyViewModel?.set_Company_Address(newString.toString())
        }

        companyEmail?.editText?.addTextChangedListener { newString ->
            dialogCreateCompanyViewModel?.set_Company_Email(newString.toString())
        }

        companyphone?.editText?.addTextChangedListener { newString ->
            dialogCreateCompanyViewModel?.set_Company_phone(newString.toString())
        }
    }


    /**
     * GET DATA ________________________________________
     */
    private fun get_Data_From_Bundle() {
        val bundle: Bundle = requireArguments()
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                if (bundle.containsKey(ARG_BUNDLE_USER)) {
                    this.user = bundle.getSerializable(ARG_BUNDLE_USER, MyUser::class.java)
                }
                if (bundle.containsKey(ARG_BUNDLE_COMPANY)) {
                    this.company = bundle.getSerializable(ARG_BUNDLE_COMPANY, MyCompany::class.java)
                }
            } else {
                if (bundle.containsKey(ARG_BUNDLE_USER)) {
                    this.user = (bundle.getSerializable(ARG_BUNDLE_USER) as MyUser)
                }
                if (bundle.containsKey(ARG_BUNDLE_COMPANY)) {
                    this.company = (bundle.getSerializable(ARG_BUNDLE_COMPANY) as MyCompany)
                }
            }

            bundle.remove(ARG_BUNDLE_USER)
            bundle.remove(ARG_BUNDLE_COMPANY)
            set_Data_In_View_Model()

        } catch (e: Exception) {
        }
    }


    /**
     * SET DATA TO VIEW MODEL ________________________________________
     */
    private fun set_Data_In_View_Model() {
        set_User_In_View_Model()
        set_Company_In_View_Model()
    }

    private fun set_User_In_View_Model() {
        dialogCreateCompanyViewModel?.set_Current_User(user)
    }

    private fun set_Company_In_View_Model() {
        dialogCreateCompanyViewModel?.set_Current_Company(company)
    }


    /**
     * UPDATE DATA TO DISPLAY ______________________________________________
     */
    private fun set_Data_To_View() {
        dialogCreateCompanyViewModel?.get_Company_Name_For_View()
            ?.observe(this) { companyNameS ->
                companyName?.editText?.setText(companyNameS)
            }
        dialogCreateCompanyViewModel?.get_Company_Address_For_View()
            ?.observe(this) { companyAddressS ->
                companyAddress?.editText?.setText(companyAddressS)
            }
        dialogCreateCompanyViewModel?.get_Company_Email_For_View()
            ?.observe(this) { companyEmailS ->
                companyEmail?.editText?.setText(companyEmailS)
            }
        dialogCreateCompanyViewModel?.get_Company_phone_For_View()
            ?.observe(this) { companyphoneS ->
                companyphone?.editText?.setText(companyphoneS)
            }
    }


    /**
     * UPDATE DATA TO DISPLAY ______________________________________________
     */
    private fun display_Error_To_View() {
        dialogCreateCompanyViewModel?.getting_Error_On_Company_Name()?.observe(this) { isError ->
            if (isError) {
                companyName?.error = requireActivity().getString(R.string.Require)
            }
        }
    }

    /**
     * UPDATE DATA TO DISPLAY ______________________________________________
     */
    private fun dialog_Close_Listener_Configuration() {
        dialogCreateCompanyViewModel?.must_Close_Dialog()?.observe(this) { mustClose ->
            if (mustClose){  dismiss()  }
        }
    }
}