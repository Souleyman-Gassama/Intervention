package com.athand.intervention.ui.account.activity

/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.athand.intervention.R
import com.athand.intervention.di.FactoryAccountViewModel
import com.athand.intervention.ui.account.fragments.login.LoginOrCreateViewModel
import com.athand.intervention.ui.account.fragments.personal_info.PersonalInfoViewModel
import com.athand.intervention.domain.PressBack
import com.athand.intervention.tools.*
import com.athand.intervention.ui.account.dialog_fragment.DialogCreateCompanyViewModel
import com.athand.intervention.ui.account.fragments.login.FragmentLogin
import com.athand.intervention.ui.account.fragments.personal_info.FragmentPersonalInfo

class AccountActivity : AppCompatActivity() {

    private lateinit var pressBack: PressBack
    private lateinit var navController: NavController
    private var destination: Int = 0

    val accountViewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        navController = get_Nav_Controller(this, R.id.nav_host_account)
        configuration_Nav_Up()

        destination_Fragment_Listener_Configuration()
        get_Destination_From_Intent()
    }

    /**
     *  CONFIGURATION PREES BACK ___________________________________________
     */
    override fun onBackPressed() {
        if (pressBack.press_Back_From_Activity()) {
            finish()
        }
    }

    private fun configuration_Nav_Up() {
        val host = get_Nav_HostFragment(this, R.id.nav_host_account)
        host.childFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
            if (fragment is PressBack) {
                pressBack = fragment
            }
        }
    }


    /**
     * GET DESTINATION FRAGMENT ___________________________________________
     */
    private fun destination_Fragment_Listener_Configuration() {
        accountViewModel.get_Destination_Fragment_Id().observe(this){
            if (it != 0) {
                destination = it
                nav_To_Fragment()
            }
        }
    }

    private fun get_Destination_From_Intent() {
        if (intent.hasExtra(KEY_BUNDLE_DESTINATION)) {
            destination = intent.getIntExtra(KEY_BUNDLE_DESTINATION, R.id.fragment_login)
            intent.removeExtra(KEY_BUNDLE_DESTINATION)
            accountViewModel.set_Destination_Fragment_Id(destination)
        }
    }


    /**
     * NAV TO FRAGMENT ___________________________________________
     */
    private fun nav_To_Fragment() {
        navController.navigate(destination)
    }
}