package com.athand.intervention.ui.account.fragments.personal_info

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.athand.intervention.R
import com.athand.intervention.authentication.AuthComponent
import com.athand.intervention.authentication.decor.AuthWithEmailAndPasswordApi
import com.athand.intervention.authentication.decor.AuthWithEmailAndPasswordDecor
import com.athand.intervention.authentication.factory.AuthFactory
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.tools.*
import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.domain.get_data.company.GetCompanyFromDatabase
import com.athand.intervention.domain.get_data.user.GetUserFromDatabase
import com.athand.intervention.domain.input_checking.CheckInputsStrategyFactory
import com.athand.intervention.domain.set_data.SetUserToDatabase
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.DataRequireStrategy.*
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck

/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */
class PersonalInfoViewModel(
    private val baseRemoteRepository: BaseRemoteRepository,
    private val baseLocalRepository: BaseLocalRepository
) : ViewModel(), ProfileDataRequire, LoginDataRequire {

//    private var authComponent: AuthComponent
    private var authComponent: AuthComponent

    private var user: MyUser? = null
    private var currentCompanyList: MutableList<MyCompany?> = mutableListOf()
    private var companyList: MutableLiveData<MutableList<MyCompany?>>
    private var newPassword: String? = ""

    private var firstNameInput: MutableLiveData<String?>
    private var lastNameInput: MutableLiveData<String?>
    private var emailInput: MutableLiveData<String?>

    private var canEditProfilData: MutableLiveData<Boolean?>
    private var canEditLoginData: MutableLiveData<Boolean?>
    private var canClickChangePofileData: MutableLiveData<Boolean?>
    private var canClickChangeLoginData: MutableLiveData<Boolean?>

    private var allErrorMap: MutableLiveData<Map<String, Boolean>>

    private var isLogin: MutableLiveData<Boolean>

    private var userIsUpdate = false
    private var authDataIsUpdate = false

    private var displayMessageInView: MutableLiveData<Any>

    private lateinit var getCompanyFromDatabase: GetCompanyFromDatabase


    init {
        authComponent = AuthFactory.create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
//        authComponent = AuthFactory().create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
                as AuthWithEmailAndPasswordDecor
//                as FirebaseAuthWithEmailAndPassword
        firstNameInput = MutableLiveData("")
        lastNameInput = MutableLiveData("")
        emailInput = MutableLiveData("")

        companyList = MutableLiveData(mutableListOf())

        isLogin = MutableLiveData(true)
        canEditProfilData = MutableLiveData(false)
        canEditLoginData = MutableLiveData(false)
        canClickChangePofileData = MutableLiveData(true)
        canClickChangeLoginData = MutableLiveData(true)

        allErrorMap = MutableLiveData(mutableMapOf())
        displayMessageInView = MutableLiveData()
    }

    override fun onCleared() {
        Log.d("eeeee", "clear")
        getCompanyFromDatabase.stop_Observe_Data()
        super.onCleared()
    }

    //GET DATA FROM DATABASE ______________________________________________
    fun get_Current_User() {
        val uid = authComponent.get_User_UID()!!
        GetUserFromDatabase(baseRemoteRepository, baseLocalRepository, uid){ result ->
            if (result.success) {
                user = result.data as MyUser
                set_Data_User_To_Views()
            }
        }
    }

    fun get_Current_Company() {
        val uid = authComponent.get_User_UID()!!
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

    override fun get_Password(): String {
        return newPassword!!
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
        if ( !user?.firstName.equals(valueString) ) {
            userIsUpdate = true
            user?.firstName = valueString
        }
    }

    fun set_Last_Name(valueString: String) {
        if ( !user?.lastName.equals(valueString) ) {
            userIsUpdate = true
            user?.lastName = valueString
        }
    }

    fun set_Email(valueString: String) {
        if ( !user?.email.equals(valueString) ) {
            userIsUpdate = true
            authDataIsUpdate = true
            user?.email = valueString
        }
    }

    fun set_Password(valueString: String) {
        if ( !newPassword.equals(valueString) ) {
            authDataIsUpdate = true
            newPassword = valueString
        }
    }

    //CLICK BUTTON PRESS BACK ______________________________________________
     fun press_Back_From_Activity(): Boolean {
        return true
    }

    //CLICK BUTTON CHANGE AUTH DATA ______________________________________________
    fun click_Change_Auth_Data() {
        if (canEditLoginData.value!!){
            canClickChangeLoginData.value = false
            val strategy =
                CheckInputsStrategyFactory(this, FOR_LOGIN, resultInputLoginData).create()
            CheckValidityOfInputsContext(strategy)
                .check_If_Data_Is_Valid()
        }else{
            set_If_Can_Edit_Login_Data(true)
        }
    }

    private val resultInputLoginData = object: ResultsOfInputCheck {
        override fun success() {
            save_New_Auth_Data()
        }

        override fun failure(errorMap: MutableMap<String, Boolean>) {
            set_Errors(errorMap)
            canClickChangeLoginData.value = true
        }
    }
    //CLICK BUTTON SAVE NEW AUTH DAT ______________________________________________
    fun save_New_Auth_Data() {
        (authComponent as AuthWithEmailAndPasswordApi).change_Auth_Data(this){
            var message: Any = it
            if (it.equals(SUCCESS_UPDATE_LOGIN_DATA)){
                message = R.string.Success_update_login_data
            }
            displayMessageInView.value = message
            canClickChangeLoginData.value = true
            set_If_Can_Edit_Login_Data(false)
        }
    }

    //CLICK BUTTON SIGN OUT ______________________________________________
    fun click_Sign_Out() {
        authComponent.sign_Out(observeAuthReponse)
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
            canClickChangePofileData.value = false
            val strategy =
                CheckInputsStrategyFactory(this, FOR_LOGIN, resultInputProfil).create()
            CheckValidityOfInputsContext(strategy)
               .check_If_Data_Is_Valid()

        } else {
            set_If_Can_Edit_Profil_Data(true)
        }
    }

    private val resultInputProfil = object: ResultsOfInputCheck {
        override fun success() {
            save_Data()
        }

        override fun failure(errorMap: MutableMap<String, Boolean>) {
            set_Errors(errorMap)
            canClickChangePofileData.value = true
        }
    }

    private fun save_Data() {
        val dateUpdate = System.currentTimeMillis()
        set_User_To_FireBase(dateUpdate)
    }

    private fun set_User_To_FireBase(dateUpdate: Long) {
        if (userIsUpdate) {
            user?.dateUpdate = dateUpdate
            SetUserToDatabase(baseRemoteRepository, baseLocalRepository, user!!){
                if (it.success) {
                    displayMessageInView.value = "Success"
                }else{
                    displayMessageInView.value = "Faillure"
                }
                canClickChangePofileData.value = true
                set_If_Can_Edit_Profil_Data(false)
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


    // IF VIEW IS CLICKABLE ________________________________________
    fun get_Click_Change_Pofile_Data(): MutableLiveData<Boolean?>{
        return canClickChangePofileData
    }

    fun get_Click_Change_Login_Data(): MutableLiveData<Boolean?>{
        return canClickChangeLoginData
    }

    // IF VIEW IS EDITABLE ________________________________________
    fun set_If_Can_Edit_Profil_Data(editable: Boolean) {
        canEditProfilData.value = editable
    }

    fun get_If_Can_Edit_Profil_Data(): MutableLiveData<Boolean?> {
        return canEditProfilData
    }


    fun set_If_Can_Edit_Login_Data(editable: Boolean) {
        canEditLoginData.value = editable
    }

    fun get_If_Can_Edit_Login_Data(): MutableLiveData<Boolean?> {
        return canEditLoginData
    }

    // IF VIEW IS EDITABLE ________________________________________
    fun dispaly_Message(): MutableLiveData<Any> {
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