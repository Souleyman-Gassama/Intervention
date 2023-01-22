package com.athand.intervention.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */
@Entity
@Keep
data class MyCompany(
    @PrimaryKey
    var id: String = "${System.currentTimeMillis()}",

    var companyName: String = "",
    var companyAddress: String = "",
    var companyEmail: String = "",
    var companyPhone: String = "",

    var uidAuthor: String = "",

    var dateCreate: Long = System.currentTimeMillis(),
    var dateUpdate: Long = dateCreate
): Serializable {

}