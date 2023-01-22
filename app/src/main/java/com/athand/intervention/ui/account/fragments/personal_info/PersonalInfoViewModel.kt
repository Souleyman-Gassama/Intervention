package com.athand.intervention.ui.account.fragments.personal_info

import android.content.Context
import androidx.lifecycle.*
import com.athand.intervention.authentication.api.AuthApi
import com.athand.intervention.authentication.api.AuthWithEmailAndPasswordApi
import com.athand.intervention.authentication.firebase.AuthWithFirebase
import com.athand.intervention.authentication.firebase.auth_option.FirebaseAuthWithEmailAndPassword
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.tools.*
//import com.athand.intervention.domain.data_require.ProfileDataRequire
import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.domain.auth.UpdateFirebaseAuthData
import com.athand.intervention.domain.get_data.company.GetCompanyFromDatabase
import com.athand.intervention.domain.get_data.user.GetUserFromDatabase
import com.athand.intervention.domain.set_data.SetUserToDatabase
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.DataRequireStrategy
import com.athand.intervention.domain.input_checking.DataRequireStrategy.*
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.google.firebase.auth.FirebaseUser

/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */
class PersonalInfoViewModel(
    private val baseRemoteRepository: BaseRemoteRepository,
    private val baseLocalRepository: BaseLocalRepository
) : ViewModel(), ProfileDataRequire, EmailDataRequire {

    private var authApi: AuthApi

    private var user: MyUser? = null
    private var currentCompanyList: MutableList<MyCompany?> = mutableListOf()
    private var companyList: MutableLiveData<MutableList<MyCompany?>>

    private var firstNameInput: MutableLiveData<String?>
    private var lastNameInput: MutableLiveData<String?>
    private var emailInput: MutableLiveData<String?>

    private var canEdit: MutableLiveData<Boolean?>

    private var allErrorMap: MutableLiveData<Map<String, Boolean>>

    private var isLogin: MutableLiveData<Boolean>

    private var userIsUpdate = false
    private var emailIsUpdate = false
    private var displayMessageInView: MutableLiveData<String>
    private lateinit var getCompanyFromDatabase: GetCompanyFromDatabase


    init {
        authApi = AuthWithFirebase.get_Instance()

        firstNameInput = MutableLiveData("")
        lastNameInput = MutableLiveData("")
        emailInput = MutableLiveData("")

        companyList = MutableLiveData(mutableListOf())

        isLogin = MutableLiveData(true)
        canEdit = MutableLiveData(false)

        allErrorMap = MutableLiveData(mutableMapOf())
        displayMessageInView = MutableLiveData()
    }

    override fun onCleared() {
        getCompanyFromDatabase.stop_Observe_Data()
        super.onCleared()
    }

    //GET DATA FROM DATABASE ______________________________________________
    fun get_Current_User() {
        val uid = authApi.get_User_UID()!!
        GetUserFromDatabase(baseRemoteRepository, baseLocalRepository, uid){ result ->
            if (result.success) {
                user = result.data as MyUser
                set_Data_User_To_Views()
            }
        }
    }

    fun get_Current_Company() {
        val uid = authApi.get_User_UID()!!
         getCompanyFromDatabase = GetCompanyFromDatabase(baseRemoteRepository, baseLocalRepository, uid){ result ->
            if (result.success) {
                currentCompanyList = result.data as MutableList<MyCompany?>
                set_Data_Company_To_Views(currentCompanyList)
            }
        }
    }

    //SETTER DATA TO VIEWS ______________________________________________
    private fun set_Data_User_To_Views() {
        firstNameInput.value = user?.firstName
        lastNameInput.value = user?.lastName
        emailInput.value = user?.email
    }

    private fun set_Data_Company_To_Views(companys: MutableList<MyCompany?>) {
        companyList.value = companys
    }


    //GETTER FOR CHECK ______________________________________________

    override fun get_Firste_Name(): String {
        return user?.firstName!!
    }

    override fun get_Last_Name(): String {
        return user?.lastName!!
    }

    override fun get_Email(): String {
        return user?.email!!
    }

    //GETTER FOR UPDATE VIEWS ______________________________________________
    fun get_Firste_Name_For_View(): MutableLiveData<String?> {
        return firstNameInput
    }

    fun get_Last_Name_For_View(): MutableLiveData<String?> {
        return lastNameInput
    }

    fun get_Email_For_View(): MutableLiveData<String?> {
        return emailInput
    }

    fun get_All_Company_For_View(): MutableLiveData<MutableList<MyCompany?>> {
        return companyList
    }


    //SETTER UPDATE FOR OBJECT ______________________________________________
    fun set_Firste_Name(valueString: String) {
        userIsUpdate = true
        user?.firstName = valueString
    }

    fun set_Last_Name(valueString: String) {
        userIsUpdate = true
        user?.lastName = valueString
    }

    fun set_Email(valueString: String) {
        userIsUpdate = true
        emailIsUpdate = true
        user?.email = valueString
    }


    //CLICK BUTTON PRESS BACK ______________________________________________
     fun press_Back_From_Activity(): Boolean {
        return true
    }

    //CLICK BUTTON CHANGE PASSWORD ______________________________________________
    fun click_Change_Password() {
        (authApi as AuthWithEmailAndPasswordApi).change_Password(emailInput.value!!)
    }

    //CLICK BUTTON SIGN OUT ______________________________________________
    fun click_Sign_Out() {
        authApi.sign_Out(observeAuthReponse)
    }

    //CLICK BUTTON ADD COMPANY ______________________________________________
    fun click_Button_Add_Company(context: Context) {
        start_Dialog_Create_Company(context, user, null, "FragmentPersonalInfo")
    }

    //CLICK BUTTON UPDATE COMPANY ______________________________________________
    fun click_Update_Company(context: Context, company: MyCompany) {
        start_Dialog_Create_Company(context, user, company, "FragmentPersonalInfo")
    }

    //CLICK BUTTON DELETE COMPANY ______________________________________________
    fun click_Delete_Company(company: MyCompany) {
        baseRemoteRepository.delete_Company(company)
            .addOnSuccessListener {
                displayMessageInView.value = SUCCESS_DELETE
                baseLocalRepository.delete_Company(company.id)
            }
    }

    //CLICK BUTTON EDIT OR SAVE ______________________________________________
    fun click_Button_Edit_Or_Save(isEditable: Boolean) {
        if (isEditable) {
           CheckValidityOfInputsContext(this)
               .check_If_Data_Is_Valid(FOR_PERSONAL_INFO, result)

        } else {
            set_If_Can_Edit(true)
        }
    }

    private val result = object: ResultsOfInputCheck {
        override fun success() {
            save_Data()
        }

        override fun failure(errorMap: MutableMap<String, Boolean>) {
            set_Errors(errorMap)
        }
    }

    private fun save_Data() {
        val dateUpdate = System.currentTimeMillis()
        set_User_To_FireBase(dateUpdate)
    }

    private fun set_User_To_FireBase(dateUpdate: Long) {
        if (emailIsUpdate){
            // Update Firebase Auth Data
            (authApi as FirebaseAuthWithEmailAndPassword).change_Email(get_Email())
        }

        if (userIsUpdate) {
            user?.dateUpdate = dateUpdate
            SetUserToDatabase(baseRemoteRepository, baseLocalRepository, user!!){
                if (it.success) {
                    displayMessageInView.value = "Success"
                }else{
                    displayMessageInView.value = "Faillure"
                }
                set_If_Can_Edit(false)
            }
        }
    }


    // FIREBASE AUTH ______________________________________________
    var observeAuthReponse = Observer<String> { reponse ->
        when (reponse) {
            SUCCESS_SIGN_OUT -> {
                isLogin.value = false
                //note: clear observe sign out
            }
        }
    }

    fun is_Login(): MutableLiveData<Boolean> {
        return isLogin
    }


    // IF VIEW IS EDITABLE ________________________________________
    fun set_If_Can_Edit(editable: Boolean) {
        canEdit.value = editable
    }

    fun get_If_Can_Edit(): MutableLiveData<Boolean?> {
        return canEdit
    }

    // IF VIEW IS EDITABLE ________________________________________
    fun dispaly_Message(): MutableLiveData<String> {
        return displayMessageInView
    }


    // ERROR ENTRIES ______________________________________________
    private fun set_Errors(errors: MutableMap<String, Boolean>) {
        allErrorMap.value = errors
    }

    fun get_Errors(): MutableLiveData<Map<String, Boolean>> {
        return allErrorMap
    }

}