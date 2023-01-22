package com.athand.intervention.ui.intervention_report.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.athand.intervention.R
import com.athand.intervention.authentication.api.AuthApi
import com.athand.intervention.authentication.firebase.AuthWithFirebase
import com.athand.intervention.tools.KEY_BUNDLE_DESTINATION
import com.athand.intervention.tools.close_Keyboard
import com.athand.intervention.tools.display_Toast
import com.athand.intervention.ui.account.activity.AccountActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

/**
 * Cree le 11/01/2023 par Gassama Souleyman
 */
class InterventionSlipActivity: AppCompatActivity(), OnClickListener {

    private lateinit var firebaseAuthApi: AuthApi
    private var isLogin: Boolean = false

    private lateinit var toolbar: Toolbar
    private lateinit var titleToolbar: TextView
    private lateinit var drawer: DrawerLayout

    private lateinit var buttonAccount: MaterialCardView
    private lateinit var textButtonAccount: MaterialTextView
    private lateinit var iconButtonAccount: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intervention_slip)
        configuration_View()
        configuration_Toolbar()
        configuration_Drawer()
        configuration_Listener()
    }

    private fun configuration_View() {
        toolbar = findViewById(R.id.toolbar_intervention_ticket)
        drawer = findViewById(R.id.intervention_ticket_drawer)
        buttonAccount = findViewById(R.id.button_account_drawer)
        textButtonAccount = findViewById(R.id.text_button_account_drawer)
        iconButtonAccount = findViewById(R.id.icon_button_account_drawer)
    }

    private fun configuration_Toolbar() {
        setSupportActionBar(toolbar)
    }

    private fun configuration_Drawer() {
        val toggle =
            ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuthApi = AuthWithFirebase.get_Instance()
        isLogin = firebaseAuthApi.is_Login()
        set_Login_Data_To_Views()
    }

    private fun configuration_Listener(){
        buttonAccount.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            buttonAccount->{
                Intent(this, AccountActivity::class.java)
                    .apply {
                        var destination = R.id.fragment_login
                        if(isLogin){
                            destination = R.id.fragment_personal_info
                        }
                        putExtra(KEY_BUNDLE_DESTINATION, destination)
                        startActivity(this)
                    }
                close_Keyboard(this)
            }
        }
    }

    fun set_Login_Data_To_Views(){
        if (isLogin) {
            textButtonAccount.text = firebaseAuthApi.get_User_Auth_Name()
            iconButtonAccount.imageTintList = ColorStateList.valueOf(getColor(R.color.green))
        }else{
            textButtonAccount.text = getString(R.string.Login)
            iconButtonAccount.imageTintList = ColorStateList.valueOf(getColor(R.color.gray))
        }
    }
}