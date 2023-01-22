package com.athand.intervention.ui.account.fragments.login

import androidx.lifecycle.*
import com.athand.intervention.authentication.api.AuthApi
import com.athand.intervention.authentication.firebase.AuthWithFirebase
import com.athand.intervention.domain.auth.LoginByEmailAndPasswordWithFirebase
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.tools.*
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.domain.auth.CreateByEmailAndPasswordWithFirebase
import com.athand.intervention.domain.auth.AuthOrCreationResult
import com.athand.intervention.domain.get_data.user.GetUserFromDatabase
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.DataRequireStrategy.LoginDataRequire
import com.athand.intervention.domain.input_checking.DataRequireStrategy.ProfileDataRequire

/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */
class LoginOrCreateViewModel(
    private val baseRemoteRepository: BaseRemoteRepository,
    private val baseLocalRepository: BaseLocalRepository
) : ViewModel(), LoginDataRequire, ProfileDataRequire {

    private lateinit var user: MyUser
    private lateinit var authApi: AuthApi

    private var viewCurrentlyDisplayed: MutableLiveData<String>
    private var authReponseForViews: MutableLiveData<String>

    private var firstNameInput: MutableLiveData<String?>
    private var lastNameInput: MutableLiveData<String?>
    private var emailInput: MutableLiveData<String?>
    private var passwordInput: MutableLiveData<String?>

    private var checkValidityEntries: CheckValidityOfInputsContext
    private var allErrorMap: MutableLiveData<Map<String, Boolean>>

    init {
        authApi = AuthWithFirebase.get_Instance()

        authReponseForViews = MutableLiveData("")

        viewCurrentlyDisplayed = MutableLiveData(INIT)

        firstNameInput = MutableLiveData("")
        lastNameInput = MutableLiveData("")
        emailInput = MutableLiveData("")
        passwordInput = MutableLiveData("")

        checkValidityEntries = CheckValidityOfInputsContext(this)
        allErrorMap = MutableLiveData(mutableMapOf())
    }

    override fun onCleared() {
        super.onCleared()
    }

    /**
     * GET DATA FOR CHECK ______________________________________________
     */
    override fun get_Firste_Name(): String {
        return firstNameInput.value!!
    }

    override fun get_Last_Name(): String {
        return lastNameInput.value!!
    }

    override fun get_Email(): String {
        return emailInput.value!!
    }

    override fun get_Password(): String {
        return passwordInput.value!!
    }

    /**
     * GET DATA FROM VIEWS ______________________________________________
     */
    fun set_Firste_Name(valueString: String) {
        firstNameInput.value = valueString
    }

    fun set_Last_Name(valueString: String) {
        lastNameInput.value = valueString
    }

    fun set_Email(valueString: String) {
        emailInput.value = valueString
    }

    fun set_Password(valueString: String) {
        passwordInput.value = valueString
    }


    /**
     * CLICK BUTTON PRESS BACK ______________________________________________
     */
    fun press_Back_From_Activity(): Boolean {
        if (viewCurrentlyDisplayed.value != INIT) {
            click_Button_Nav_Up()
            return false
        }
        return true
    }

    fun click_Button_Nav_Up() {
        viewCurrentlyDisplayed.value = INIT
    }


    /**
     * CLICK BUTTON LOGIN ______________________________________________
     */
    fun click_Button_Login() {
        if (viewCurrentlyDisplayed.value == INIT) {
            viewCurrentlyDisplayed.value = FOR_LOGIN
            (authApi as AuthWithFirebase).auth_With(AUTH_EMAIL_AND_PASSWORD)

        } else {
            LoginByEmailAndPasswordWithFirebase(this){
                reponse_Login_Or_Create(it)
            }
        }
    }

    /**
     * CLICK BUTTON CREATE ______________________________________________
     */
    fun click_Button_Create() {
        if (viewCurrentlyDisplayed.value == INIT) {
            viewCurrentlyDisplayed.value = FOR_CREATE
            (authApi as AuthWithFirebase).auth_With(AUTH_EMAIL_AND_PASSWORD)
        } else {
            CreateByEmailAndPasswordWithFirebase(this){
                reponse_Login_Or_Create(it)
            }
        }
    }


    /**
     * FIREBASE AUTH ______________________________________________
     */
    fun reponse_Login_Or_Create(result: AuthOrCreationResult) {
        if(result.isSuccessful){
           success_Auth(result.message!!)
        }else{
            error_Auth(result)
        }
    }

    private fun success_Auth(message: String) {
        when (message) {
            SUCCESS_LOGIN -> {
                get_User_From_Database()
            }

            SUCCESS_CREATE -> {
                set_User_Object_To_FireBase()
            }
        }
        authReponseForViews.value = message
        authReponseForViews.value = ""
    }

    private fun error_Auth(result: AuthOrCreationResult) {
        when(result.message){
            ERROR_INPUT -> {
                set_Errors(result.inputErrorMap)
            }
        }
    }


    //Note: Update display based on authentication
    fun get_Auth_Reponse(): MutableLiveData<String> {
        return authReponseForViews
    }

    /**
     * GET DATA FROM DATABASE ______________________________________________
     */
    private fun get_User_From_Database(){
        val uid = authApi.get_User_UID()!!
        GetUserFromDatabase(baseRemoteRepository, baseLocalRepository, uid){ data ->
            if (data.success) {
            }else{
            }
        }
    }


    /**
     * SET DATA IN DATABASE ______________________________________________
     */
    private fun set_User_Object_To_FireBase() {
        create_User_Object()
        baseRemoteRepository.set_User(user)
            .addOnSuccessListener {
                set_User_Object_To_Local_Database()
            }
            .addOnFailureListener {
            }
    }

    private fun set_User_Object_To_Local_Database() {
        baseLocalRepository.set_User(user)
    }


    /**
     * CREATE USER ______________________________________________
     */
    private fun create_User_Object() {
        val uid = authApi.get_User_UID()!!
        user = MyUser(
            id = "$uid:${System.currentTimeMillis()}",
            uid = uid,
            firstName = firstNameInput.value!!,
            lastName = lastNameInput.value!!,
            email = emailInput.value!!
        )
    }


    /**
     * SET VIEWS VISIBILITY ______________________________________________
     */
    fun make_Views_Visible(): MutableLiveData<String> {
        return viewCurrentlyDisplayed
    }


    /**
     *SET INPUT ERRORS ON VIEWS ______________________________________________
     */
    private fun set_Errors(errors: MutableMap<String, Boolean>) {
        allErrorMap.value = errors
    }

    fun get_Errors(): MutableLiveData<Map<String, Boolean>> {
        return allErrorMap
    }

}