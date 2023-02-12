package com.athand.intervention.ui.account.fragments.personal_info

/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.athand.intervention.R
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.di.FactoryAccountViewModel
import com.athand.intervention.domain.PressBack
import com.athand.intervention.tools.*
import com.athand.intervention.ui.account.fragments.personal_info.adapter.CompanysRecyclerViewAdapter
import com.athand.intervention.ui.account.fragments.personal_info.adapter.CompanysRecyclerViewAdapter.CallbackCompanyRecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class FragmentPersonalInfo : Fragment(), OnClickListener, CallbackCompanyRecyclerView, PressBack {

    private val personalInfoViewModel: PersonalInfoViewModel by viewModels {
        FactoryAccountViewModel.get_Factory(PERSONAL_INFO)
    }

    private var buttonNavUp: ImageButton? = null

    private var firstName: TextInputLayout? = null
    private var lastName: TextInputLayout? = null
    private var email: TextInputLayout? = null
    private var password: TextInputLayout? = null

    private var recyclerView: RecyclerView? = null
    private var buttonChangeAuthData: MaterialButton? = null

    private var buttonEditOrSave: MaterialButton? = null
    private var buttonCreateCompany: FloatingActionButton? = null

    private var buttonSignOut: RelativeLayout? = null

    private var companyList: MutableList<MyCompany?> = mutableListOf()
    private var isEditableProfil: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_info, container, false)
        configuration_View(view)
        init_Of_View_Visibility(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuration_Listener()
        configuration_Recycler_View()
        configuration_Listener_Error()
        configuration_Listener_Enable_View()
        configuration_Listener_Can_Click_Button()
        configuration_Listener_Edit_Text()
        configuration_Listener_Auth_Statue()
        get_Data()
        set_Data_To_View()
        dispaly_Message()
    }

    override fun press_Back_From_Activity(): Boolean {
        return personalInfoViewModel.press_Back_From_Activity()
    }


    private fun configuration_View(view: View?) {
        buttonNavUp = view?.findViewById(R.id.button_nav_up_view_top)!!

        firstName = view.findViewById(R.id.first_name_layout_personal_info)
        lastName = view.findViewById(R.id.last_name_layout_personal_info)
        email = view.findViewById(R.id.email_layout_login)
        password = view.findViewById(R.id.password_layout_login)

        recyclerView = view.findViewById(R.id.recycler_view_list)

        buttonChangeAuthData = view.findViewById(R.id.button_change_auth_data_fragment_personal_info)
        buttonEditOrSave = view.findViewById(R.id.button_Edit_Or_Save_fragment_personal_info)
        buttonCreateCompany = view.findViewById(R.id.button_add_company_fragment_personal_info)

        buttonSignOut = view.findViewById(R.id.button_sign_out)
    }


    /**
     * CONFIGURATION LISTENER STATUE AUTH ______________________________________________
     */
    private fun configuration_Listener_Auth_Statue() {
        personalInfoViewModel.is_Login().observe(requireActivity()) {
            if (!it) {
                requireActivity().finish()
            }
        }
    }

    /**
     * CONFIGURATION LISTENER EDITE TEXT______________________________________________
     */
    private fun configuration_Listener_Edit_Text() {
        firstName?.editText?.addTextChangedListener { newString ->
            personalInfoViewModel.set_Firste_Name(newString.toString())
            firstName?.error = ""
        }

        lastName?.editText?.addTextChangedListener { newString ->
            personalInfoViewModel.set_Last_Name(newString.toString())
            lastName?.error = ""
        }

        email?.editText?.addTextChangedListener { newString ->
            personalInfoViewModel.set_Email(newString.toString())
            email?.error = ""
        }

        password?.editText?.addTextChangedListener { newString ->
            personalInfoViewModel.set_Password(newString.toString())
            password?.error = ""
        }
    }

    /**
     * CONFIGURATION RECYCLER VIEW ______________________________________________
     */
    private fun configuration_Recycler_View() {
        recyclerView?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView?.setNestedScrollingEnabled(false);

    }

    private fun update_Recycler_View() {
        recyclerView?.adapter = CompanysRecyclerViewAdapter(companyList)
    }


    /**
     * CONFIGURATION LISTENER BUTTON______________________________________________
     */
    private fun configuration_Listener() {
        buttonNavUp?.setOnClickListener(this)
        buttonChangeAuthData?.setOnClickListener(this)
        buttonEditOrSave?.setOnClickListener(this)
        buttonCreateCompany?.setOnClickListener(this)
        buttonSignOut?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            buttonNavUp -> {
                requireActivity().finish()
            }
            buttonChangeAuthData -> {
                personalInfoViewModel.click_Change_Auth_Data()
            }

            buttonEditOrSave -> {
                personalInfoViewModel.click_Button_Edit_Or_Save(isEditableProfil)
            }

            buttonCreateCompany -> {
                personalInfoViewModel.click_Button_Add_Company(requireContext())
            }

            buttonSignOut -> {
                personalInfoViewModel.click_Sign_Out()
            }
        }
    }

    override fun click_Update_Company(company: MyCompany) {
        personalInfoViewModel.click_Update_Company(requireContext(), company)
    }

    override fun click_Delete_Company(company: MyCompany) {
        personalInfoViewModel.click_Delete_Company(company)
    }

    /**
     * CONFIGURATION ENABLE BUTTON ______________________________________________
     */
    private fun configuration_Listener_Can_Click_Button() {
        personalInfoViewModel.get_Click_Change_Pofile_Data().observe(viewLifecycleOwner) { clickable ->
            set_Enable_View(buttonEditOrSave, clickable!!)
        }

        personalInfoViewModel.get_Click_Change_Login_Data().observe(viewLifecycleOwner) { clickable ->
            set_Enable_View(buttonChangeAuthData, clickable!!)
        }
    }


    /**
     * CONFIGURATION EDITABLE ______________________________________________
     */
    private fun configuration_Listener_Enable_View() {
        personalInfoViewModel.get_If_Can_Edit_Profil_Data().observe(viewLifecycleOwner) { editable ->
            isEditableProfil = editable!!
            enable_All_Profil_View(isEditableProfil)
            update_Data_Button_Edit_Or_Save(isEditableProfil)
        }

        personalInfoViewModel.get_If_Can_Edit_Login_Data().observe(viewLifecycleOwner) { editable ->
//            isEditableLogin = editable!!
//            enable_All_Login_View(isEditableLogin)
            enable_All_Login_View(editable!!)
        }
    }

    private fun enable_All_Profil_View(enable: Boolean) {
        set_Enable_Edite_Text(firstName, enable)
        set_Enable_Edite_Text(lastName, enable)
    }

    private fun enable_All_Login_View(enable: Boolean) {
        set_Enable_Edite_Text(email, enable)
        set_Visibility_View(password, enable)
    }

    private fun set_Enable_View(view: View?, enable: Boolean) {
        view?.isEnabled = enable
    }

    private fun set_Enable_Edite_Text(view: TextInputLayout?, enable: Boolean) {
        set_Enable_View(view?.editText!!, enable)
        set_Strock_Edite_Text(view, enable)
    }

    private fun set_Strock_Edite_Text(view: TextInputLayout?, enable: Boolean) {
        if (enable) {
            view?.boxStrokeWidth = 3
            view?.boxStrokeWidthFocused = 5
        } else {
            view?.boxStrokeWidth = 0
            view?.boxStrokeWidthFocused = 0
        }
    }

    private fun update_Data_Button_Edit_Or_Save(editable: Boolean) {
        var text: String
        var backgroundColor: Int
        var textColor: Int
        var icon: Drawable?

        if (editable) {
            text = requireActivity().getString(R.string.Save)
            backgroundColor = requireActivity().getColor(R.color.blue)
            textColor = requireActivity().getColor(R.color.white)
            icon = requireActivity().getDrawable(R.drawable.ic_save)
        } else {
            text = requireActivity().getString(R.string.Edit)
            backgroundColor = requireActivity().getColor(R.color.white)
            textColor = requireActivity().getColor(R.color.blue)
            icon = requireActivity().getDrawable(R.drawable.ic_edit)
        }
        set_Data_To_Button_Edit_Or_Save(text, backgroundColor, textColor, icon)
    }

    fun set_Data_To_Button_Edit_Or_Save(text: String, backgroundColor: Int, textColor: Int, icon: Drawable?){
        buttonEditOrSave?.setBackgroundColor(backgroundColor)
        buttonEditOrSave?.setText(text)
        buttonEditOrSave?.setTextColor(textColor)
        buttonEditOrSave?.iconTint = ColorStateList.valueOf(textColor)
        buttonEditOrSave?.icon = icon
    }


    /**
     * GET DATA ______________________________________________
     */
    private fun get_Data(){
        personalInfoViewModel.get_Current_User()
        personalInfoViewModel.get_Current_Company()
    }


    /**
     * UPDATE DATA TO DISPLAY ______________________________________________
     */
    private fun set_Data_To_View() {
        personalInfoViewModel.get_Firste_Name_For_View().observe(viewLifecycleOwner) { firstNameS ->
            firstName?.editText?.setText(firstNameS)
        }
        personalInfoViewModel.get_Last_Name_For_View().observe(viewLifecycleOwner) { lastNameS ->
            lastName?.editText?.setText(lastNameS)
        }
        personalInfoViewModel.get_Email_For_View().observe(viewLifecycleOwner) { emailS ->
            email?.editText?.setText(emailS)
        }

        personalInfoViewModel.get_All_Company_For_View().observe(viewLifecycleOwner) { list ->
            companyList = list!!
            update_Recycler_View()
        }
    }

    private fun dispaly_Message(){
        personalInfoViewModel.dispaly_Message().observe(viewLifecycleOwner){
            var message: String = ""
            when(it){
                is Int ->{
                    message = requireActivity().getString(R.string.Success_update_login_data)
                }
                SUCCESS_DELETE ->{
                    message = requireActivity().getString(R.string.Successfully_deleted)
                }
                else ->{
                    message = it.toString()
                }
            }
            display_Toast(requireContext(), message)
        }
    }

    /**
     * VISIBILITY VIEW ______________________________________________
     */
    private fun init_Of_View_Visibility(view: View?) {
        view?.findViewById<RelativeLayout>(R.id.button_reset_password)?.visibility = GONE
    }


    /**
     * SET ERROR ______________________________________________
     */
    private fun configuration_Listener_Error() {
        personalInfoViewModel.get_Errors().observe(viewLifecycleOwner) { errorMap ->
            if (errorMap.size > 0) {
                val errorMapFilter = errorMap.filterValues { value -> value == true }
                errorMapFilter.keys.forEach { key ->
                    when (key) {
                        KEY_EMAIL -> {
                            set_Error_To_View(email)
                        }
                        KEY_FIRST_NAME -> {
                            set_Error_To_View(firstName)
                        }
                        KEY_LAST_NAME -> {
                            set_Error_To_View(lastName)
                        }
                        KEY_PASSWORD -> {
                            set_Error_To_View(password)
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