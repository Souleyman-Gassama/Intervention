package com.athand.intervention.data.entity

import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Cree le 27/12/2022 par Gassama Souleyman
 */
@Entity
@Keep
data class MyUser(
    @PrimaryKey
    var id: String = "",
    var uid: String = "",

    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",

    var dateCreate: Long = System.currentTimeMillis(),
    var dateUpdate: Long = System.currentTimeMillis()
): Serializable {

}