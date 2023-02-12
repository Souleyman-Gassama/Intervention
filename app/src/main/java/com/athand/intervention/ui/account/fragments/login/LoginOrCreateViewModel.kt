package com.athand.intervention.ui.account.fragments.login

import androidx.core.view.accessibility.AccessibilityClickableSpanCompat
import androidx.lifecycle.*
import com.athand.intervention.R
import com.athand.intervention.authentication.AuthComponent
import com.athand.intervention.authentication.factory.AuthFactory
import com.athand.intervention.domain.auth_use_case.LoginByEmailAndPasswordWithFirebase
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.tools.*
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.domain.auth_use_case.CreateByEmailAndPasswordWithFirebase
import com.athand.intervention.domain.auth_use_case.AuthOrCreationResult
import com.athand.intervention.domain.get_data.user.GetUserFromDatabase
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
    private lateinit var authComponent: AuthComponent

    private var viewCurrentlyDisplayed: MutableLiveData<String>

    private var canClickButtonLogin: MutableLiveData<Boolean>
    private var canClickButtonCreate: MutableLiveData<Boolean>
    private var messageForViews: MutableLiveData<Any>
    private var navigation: MutableLiveData<Int>

    private var firstNameInput: MutableLiveData<String?>
    private var lastNameInput: MutableLiveData<String?>
    private var emailInput: MutableLiveData<String?>
    private var passwordInput: MutableLiveData<String?>
    private var allErrorMap: MutableLiveData<Map<String, Boolean>>

    init {
        viewCurrentlyDisplayed = MutableLiveData(INIT)

        canClickButtonLogin = MutableLiveData(true)
        canClickButtonCreate = MutableLiveData(true)
        messageForViews = MutableLiveData()
        navigation = MutableLiveData()

        firstNameInput = MutableLiveData("")
        lastNameInput = MutableLiveData("")
        emailInput = MutableLiveData("")
        passwordInput = MutableLiveData("")

        allErrorMap = MutableLiveData(mutableMapOf())
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
            authComponent = AuthFactory.create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
//            authComponent = AuthFactory().create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)

        } else {
            set_If_Can_Click_Button(false)
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
            authComponent = AuthFactory.create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
//            authComponent = AuthFactory().create(FIREBASE_AUTH_COMPONENT, AUTH_DECOR_EMAIL_AND_PASSWORD)
        } else {
            set_If_Can_Click_Button(false)
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
                messageForViews.value = R.string.Login_Successful
                navigation.value = DIRECTION_INTERVENTION_SLIP_ACTIVITY
                get_User_From_Database()
            }

            SUCCESS_CREATE -> {
                messageForViews.value = R.string.Create_Successfully
                navigation.value = DIRECTION_INTERVENTION_SLIP_ACTIVITY
                set_User_Object_To_FireBase()
            }
        }
    }

    private fun error_Auth(result: AuthOrCreationResult) {
        when(result.message){
            ERROR_INPUT -> {
                set_Errors(result.inputErrorMap)
            }
            FAILURE_LOGIN ->{
                messageForViews.value = R.string.Failure_login
            }
            else -> {
                messageForViews.value = result.message!!
            }
        }
        set_If_Can_Click_Button(true)
    }


    /**
     * GET DATA FROM DATABASE ______________________________________________
     */
    private fun get_User_From_Database(){
        val uid = authComponent.get_User_UID()!!
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
        val uid = authComponent.get_User_UID()!!
        user = MyUser(
            id = "$uid:${System.currentTimeMillis()}",
            uid = uid,
            firstName = firstNameInput.value!!,
            lastName = lastNameInput.value!!,
            email = emailInput.value!!
        )
    }


    /**
     * SET IF CAN CLICK BUTTON ______________________________________________
     */
    fun set_If_Can_Click_Button(clickable: Boolean){
        when(viewCurrentlyDisplayed.value) {
            FOR_LOGIN -> {
                canClickButtonLogin.value = clickable
            }
            FOR_CREATE -> {
                canClickButtonCreate . value = clickable
            }
        }
    }

    fun can_Click_Button_Login(): MutableLiveData<Boolean>{
        return canClickButtonLogin
    }
    fun can_Click_Button_Create(): MutableLiveData<Boolean>{
        return canClickButtonCreate
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


    /**
     *SET MESSAGE ON VIEWS ______________________________________________
     */
    fun make_Message_On_View(): MutableLiveData<Any> {
        return messageForViews
    }

    /**
     *SET NAVIGATION ON APP ______________________________________________
     */
    fun navigation_In_App(): MutableLiveData<Int> {
        return navigation
    }

}