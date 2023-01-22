package com.athand.intervention.tools

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
import com.athand.intervention.ui.account.dialog_fragment.DialogCreateCompany

fun start_Dialog_Create_Company(context: Context, user: MyUser?, company: MyCompany?, tag: String?)
: DialogCreateCompany {

        val dialogCreateCategory = DialogCreateCompany()
        val arg = Bundle()
        if (user != null) {
            arg.putSerializable(ARG_BUNDLE_USER, user)
        }
        if (company != null) {
            arg.putSerializable(ARG_BUNDLE_COMPANY, company)
        }
        dialogCreateCategory.arguments = arg
        dialogCreateCategory.show((context as AppCompatActivity).supportFragmentManager, tag)
        return dialogCreateCategory
}