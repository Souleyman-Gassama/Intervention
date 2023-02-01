package com.athand.intervention.ui.account.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.athand.intervention.R
import com.athand.intervention.di.FactoryAccountViewModel
import com.athand.intervention.domain.PressBack
import com.athand.intervention.tools.*
import com.athand.intervention.ui.intervention_report.activity.InterventionSlipActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */

class FragmentLogin : Fragment(), View.OnClickListener, PressBack {

    private val loginViewModel:
        LoginOrCreateViewModel by viewModels {FactoryAccountViewModel.get_Factory(LOGIN_OR_CREATE)}

    private var buttonNavUp: ImageButton? = null

    private var layoutInputPersonalInfo: FrameLayout? = null
    private var firstName: TextInputLayout? = null
    private var lastName: TextInputLayout? = null

    private var layoutInputLogin: FrameLayout? = null
    private var email: TextInputLayout? = null
    private var password: TextInputLayout? = null
    private var buttonResetPassword: RelativeLayout? = null

    private var layoutButton: FrameLayout? = null
    private var buttonLogin: MaterialButton? = null
    private var buttonCreate: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        configuration_View(view)
        configuration_Listener_Edit_Text()
        buttonNavUp?.visibility = GONE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuration_Listener()
        configuration_Listener_Views_Visibility()
        display_Message()
        navigation_In_App()
        configuration_Listener_Error()
    }

    override fun press_Back_From_Activity(): Boolean {
        return loginViewModel.press_Back_From_Activity()
    }

    private fun configuration_View(view: View?) {
        buttonNavUp = view?.findViewById(R.id.button_nav_up_view_top)!!

        layoutInputPersonalInfo = view.findViewById(R.id.layout_input_personal_info_fragment_login)
        firstName = view.findViewById(R.id.first_name_layout_personal_info)
        lastName = view.findViewById(R.id.last_name_layout_personal_info)

        layoutInputLogin = view.findViewById(R.id.layout_input_login_fragment_login)
        email = view.findViewById(R.id.email_layout_login)
        password = view.findViewById(R.id.password_layout_login)
        buttonResetPassword = view.findViewById(R.id.button_reset_password)

        layoutButton = view.findViewById(R.id.layout_button_account_fragment_login)
        buttonLogin = view.findViewById(R.id.button_login)
        buttonCreate = view.findViewById(R.id.button_create)
    }


    /**
     * CONFIGURATION LISTENER EDITE TEXT______________________________________________
     */
    private fun configuration_Listener_Edit_Text() {
        firstName?.editText?.addTextChangedListener { newString ->
            loginViewModel.set_Firste_Name(newString.toString())
            remove_Error(firstName!!)
        }

        lastName?.editText?.addTextChangedListener { newString ->
            loginViewModel.set_Last_Name(newString.toString())
            remove_Error(lastName!!)
        }

        email?.editText?.addTextChangedListener { newString ->
            loginViewModel.set_Email(newString.toString())
            remove_Error(email!!)
        }

        password?.editText?.addTextChangedListener { newString ->
            loginViewModel.set_Password(newString.toString())
            remove_Error(password!!)
        }
    }


    /**
     * CONFIGURATION LISTENER BUTTON______________________________________________
     */
    private fun configuration_Listener() {
        buttonNavUp?.setOnClickListener(this)
        buttonLogin?.setOnClickListener(this)
        buttonResetPassword?.setOnClickListener(this)
        buttonCreate?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonNavUp -> {
                loginViewModel.click_Button_Nav_Up()
            }

            buttonLogin -> {
                loginViewModel.click_Button_Login()
            }

            buttonResetPassword -> {}

            buttonCreate -> {
                loginViewModel.click_Button_Create()
            }
        }
    }

    private fun navigation_In_App() {
        loginViewModel.navigation_In_App().observe(viewLifecycleOwner) { direction ->
            if (direction == DIRECTION_INTERVENTION_SLIP_ACTIVITY){
                nav_To_InterventionTicketActivity(requireActivity())
            }
        }
    }

    private fun nav_To_InterventionTicketActivity(activity: FragmentActivity) {
        Intent(activity, InterventionSlipActivity::class.java)
            .apply { startActivity(this) }
    }


    /**
     * UPDATE DISPLAY ______________________________________________
     */
    private fun configuration_Listener_Views_Visibility() {
        loginViewModel.make_Views_Visible().observe(viewLifecycleOwner) { viewToDisplay ->
            when (viewToDisplay) {
                INIT -> {
                    reset_Visibility_All_View()
                }
                FOR_LOGIN -> {
                    make_Visible_Views_For_Connection()
                }
                FOR_CREATE -> {
                    make_Visible_Views_For_Create_Account()
                }
            }
        }
    }

    private fun reset_Visibility_All_View() {
        buttonNavUp?.visibility = GONE

        layoutInputPersonalInfo?.visibility = GONE
        layoutInputLogin?.visibility = GONE

        buttonLogin?.visibility = VISIBLE
        buttonCreate?.visibility = VISIBLE

        buttonCreate?.setBackgroundColor(requireActivity().getColor(R.color.white))
        buttonCreate?.setTextColor(requireActivity().getColor(R.color.blue))

        remove_Errors_From_All_Views()
        close_Keyboard(requireActivity())
    }

    private fun make_Visible_Views_For_Connection() {
        buttonNavUp?.visibility = VISIBLE

        layoutInputPersonalInfo?.visibility = GONE
        layoutInputLogin?.visibility = VISIBLE

        buttonCreate?.visibility = GONE
    }

    private fun make_Visible_Views_For_Create_Account() {
        buttonNavUp?.visibility = VISIBLE

        layoutInputPersonalInfo?.visibility = VISIBLE
        layoutInputLogin?.visibility = VISIBLE
        buttonResetPassword?.visibility = GONE

        buttonLogin?.visibility = GONE
        buttonCreate?.setBackgroundColor(requireActivity().getColor(R.color.blue))
        buttonCreate?.setTextColor(requireActivity().getColor(R.color.white))
    }

    private fun remove_Errors_From_All_Views() {
        remove_Error(firstName!!)
        remove_Error(lastName!!)
        remove_Error(email!!)
        remove_Error(password!!)
    }

    private fun remove_Error(view: TextInputLayout) {
        view.error = ""
//        view.isErrorEnabled = false
    }

    private fun display_Message() {
        loginViewModel.make_Message_On_View().observe(viewLifecycleOwner) { message ->
            var text = message.toString()
            if (message is Int){
                text = requireActivity().getString(message)
            }
            display_Toast(requireContext(), text)
        }
    }


    /**
     * SET ERROR ______________________________________________
     */
    private fun configuration_Listener_Error() {
        loginViewModel.get_Errors().observe(viewLifecycleOwner) { errorMap ->
            if (errorMap.size > 0) {
                val errorMapFilter = errorMap.filterValues { value -> value == true }
                errorMapFilter.keys.forEach { key ->
                    when (key) {
                        KEY_EMAIL -> {
                            set_Error_To_View(email)
                        }
                        KEY_PASSWORD -> {
                            password?.error = requireActivity().getString(R.string.Six_character_required)
                        }
                        KEY_FIRST_NAME -> {
                            set_Error_To_View(firstName)
                        }
                        KEY_LAST_NAME -> {
                            set_Error_To_View(lastName)
                        }
                    }
                }
            }
        }
    }

    private fun set_Error_To_View(view: TextInputLayout?) {
        view?.error = requireActivity().getString(R.string.Require)
    }

}