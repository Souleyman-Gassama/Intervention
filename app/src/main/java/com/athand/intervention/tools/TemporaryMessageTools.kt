package com.athand.intervention.tools
/**
 * Cree le 28/12/2022 par Gassama Souleyman
 */
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.athand.intervention.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun display_Toast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun display_Message_SnackBar(view: View?, text: String?) {
    Snackbar
        .make(view!!, text!!, BaseTransientBottomBar.LENGTH_LONG)
        .setBackgroundTint(view.context.getColor(R.color.blueDark))
        .show()
}