package com.athand.intervention.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.athand.intervention.ui.account.fragments.login.LoginOrCreateViewModel
import com.athand.intervention.ui.account.fragments.personal_info.PersonalInfoViewModel
import com.athand.intervention.tools.CREATE_COMPANY
import com.athand.intervention.tools.LOGIN_OR_CREATE
import com.athand.intervention.tools.PERSONAL_INFO
import com.athand.intervention.ui.account.dialog_fragment.DialogCreateCompanyViewModel

/**
 * Cree le 28/12/2022 par Gassama Souleyman
 */
class FactoryAccountViewModel() : ViewModelProvider.Factory {

    companion object {

        fun get_Factory(currentViewModel: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                    val application = checkNotNull(extras[APPLICATION_KEY])
                    val baseRemoteRepository = inject_BaseRemoteRepository()
                    val baseLocalRepository = inject_BaseLocalRepository(application)

                    when (currentViewModel) {
                        PERSONAL_INFO -> {
                            return PersonalInfoViewModel(baseRemoteRepository, baseLocalRepository) as T
                        }
                        LOGIN_OR_CREATE -> {
                            return LoginOrCreateViewModel(baseRemoteRepository, baseLocalRepository) as T
                        }
                        CREATE_COMPANY -> {
                            return DialogCreateCompanyViewModel(baseRemoteRepository, baseLocalRepository) as T
                        }
                        else -> {
                            throw IllegalArgumentException("Unknown ViewModel class")
                        }
                    }
                }
            }
        }
    }
}