package com.athand.intervention.tools
/**
 * Cree le 28/12/2022 par Gassama Souleyman
 */
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

fun convert_Date_And_Hour_Long_To_String(dateLong: Long, format: SimpleDateFormat?): String? {
    var simpleDateFormat: SimpleDateFormat
    if (format == null) {
        simpleDateFormat = SimpleDateFormat("ddMMYYYY'-'HH_mm_ss")
    } else {
        simpleDateFormat = format
    }
    val date = Date(dateLong)
    return simpleDateFormat.format(date)
}

fun close_Keyboard(activity: Activity) {
    val view = activity.currentFocus
    if (view != null) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}