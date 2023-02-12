package com.athand.intervention.ui.account.dialog_fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.athand.intervention.data.repository.BaseLocalRepository
import com.athand.intervention.data.repository.BaseRemoteRepository
import com.athand.intervention.domain.input_checking.DataRequireStrategy.CompanyDataRequire
//import com.athand.intervention.domain.data_require.CompanyDataRequire
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.domain.input_checking.CheckInputsStrategyFactory
import com.athand.intervention.domain.input_checking.CheckValidityOfInputsContext
import com.athand.intervention.domain.input_checking.concrete_strategys.ResultsOfInputCheck
import com.athand.intervention.tools.FOR_DIALOG_CREATE_COMPANY
import com.athand.intervention.tools.FOR_LOGIN

class DialogCreateCompanyViewModel(
    val baseRemoteRepository: BaseRemoteRepository,
    val baseLocalRepository: BaseLocalRepository
) : ViewModel(), CompanyDataRequire {

    private var currentUser: MyUser? = null
    private var currentCompany: MyCompany? = null

    private var companyNameInput: MutableLiveData<String?>
    private var companyAddressInput: MutableLiveData<String?>
    private var companyEmailInput: MutableLiveData<String?>
    private var companyPhoneInput: MutableLiveData<String?>

    private var companyIsUpdate: Boolean = false

    private var closeDialog: MutableLiveData<Boolean>
    private var errorCompanyName: MutableLiveData<Boolean>

    init {
        companyNameInput = MutableLiveData("")
        companyAddressInput = MutableLiveData("")
        companyEmailInput = MutableLiveData("")
        companyPhoneInput = MutableLiveData("")
        errorCompanyName = MutableLiveData(false)
        closeDialog = MutableLiveData(false)
    }

    override fun onCleared() {
        super.onCleared()
    }

    /**
     * GET USER FROM VIEW ______________________________
     */
    fun set_Current_User(user: MyUser?) {
        if (currentUser == null) {
            currentUser = user
        }
    }


    /**
     * GET COMPANY FROM VIEW ______________________________
     */
    fun set_Current_Company(company: MyCompany?) {
        // note: Je verifie pour ne pas suprimer les donne lors  dun changement de config (rotation)
        if (currentCompany == null) {
            currentCompany = company
        }
        check_If_Is_A_New_Company()
    }

    private fun check_If_Is_A_New_Company() {
        if (currentCompany != null) {
            set_Company_Data_To_Display()
        } else {
            create_New_Company()
        }
    }

    private fun set_Company_Data_To_Display() {
        companyNameInput.value = currentCompany?.companyName
        companyAddressInput.value = currentCompany?.companyAddress
        companyEmailInput.value = currentCompany?.companyEmail
        companyPhoneInput.value = currentCompany?.companyPhone
    }

    private fun create_New_Company() {
        currentCompany = MyCompany(
            uidAuthor = currentUser?.uid!!
        )
    }


    //GETTER FOR CHECK ______________________________________________
    override fun get_Company_Name(): String {
        return currentCompany?.companyName!!
    }

    //GETTER FOR UPDATE VIEWS ______________________________________________
    fun get_Company_Name_For_View(): MutableLiveData<String?> {
        return companyNameInput
    }

    fun get_Company_Address_For_View(): MutableLiveData<String?> {
        return companyAddressInput
    }

    fun get_Company_Email_For_View(): MutableLiveData<String?> {
        return companyEmailInput
    }

    fun get_Company_phone_For_View(): MutableLiveData<String?> {
        return companyPhoneInput
    }


    //SETTER UPDATE FOR OBJECT ______________________________________________
    fun set_Company_Name(valueString: String) {
        if (!currentCompany?.companyName.contentEquals(valueString)) {
            companyIsUpdate = true
            currentCompany?.companyName = valueString
        }
    }

    fun set_Company_Address(valueString: String) {
        if (!currentCompany?.companyAddress.contentEquals(valueString)) {
            currentCompany?.companyAddress = valueString
            companyIsUpdate = true
        }
    }

    fun set_Company_Email(valueString: String) {
        if (!currentCompany?.companyEmail.contentEquals(valueString)) {
            currentCompany?.companyEmail = valueString
            companyIsUpdate = true
        }
    }

    fun set_Company_phone(valueString: String) {
        if (!currentCompany?.companyPhone.contentEquals(valueString)) {
            currentCompany?.companyPhone = valueString
            companyIsUpdate = true
        }
    }


    //CLICK CREATE COMPANY  ______________________________________________
    fun click_Create_Company() {
        val strategy =
            CheckInputsStrategyFactory(this, FOR_LOGIN, result).create()
        CheckValidityOfInputsContext(strategy)
            .check_If_Data_Is_Valid()
    }

    val result = object : ResultsOfInputCheck {
        override fun success() {
            save_Data()
        }

        override fun failure(errorMap: MutableMap<String, Boolean>) {
            set_Errors(errorMap)
        }
    }

    private fun save_Data() {
        val dateUpdate = System.currentTimeMillis()
        set_Companys_To_FireBase(dateUpdate)
    }

    private fun set_Companys_To_FireBase(dateUpdate: Long) {
        if (companyIsUpdate) {
            currentCompany?.dateUpdate = dateUpdate
            baseRemoteRepository.set_Company(currentCompany!!)
                .addOnSuccessListener{
                    set_Companys_To_Room()
                    closeDialog.value = true
                }
        }
    }

    private fun set_Companys_To_Room() {
        baseLocalRepository.set_Company(currentCompany!!)
    }


    // ERROR ENTRIES ______________________________________________
    private fun set_Errors(errors: MutableMap<String, Boolean>) {
        errors
            .filter { (key, value) -> value == true }
            .forEach {
                when (it.key) {
                    "company" -> {
                        errorCompanyName.value = true
                    }
                }
            }
    }

    fun getting_Error_On_Company_Name(): MutableLiveData<Boolean> {
        return errorCompanyName
    }

    /**
     * DISMISS DIALOG ______________________________________________
     */
    fun must_Close_Dialog(): MutableLiveData<Boolean>{
        return closeDialog
    }
}