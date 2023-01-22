package com.athand.intervention.data.database.local.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.athand.intervention.data.entity.MyUser
/**
 * Cree le 11/01/2023 par Gassama Souleyman
 */
@Dao
interface MyUserDao {

    /**
     * CREATE ____________________________________________________________________________________________
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inset_User(user: MyUser?)

    /**
     * GET _______________________________________________________________________________________________
     */
    @Query("SELECT * FROM MyUser WHERE uid IN (:userUid)")
    fun get_User(userUid: String): LiveData<MyUser>?

    /**
     * UPDATE ____________________________________________________________________________________________
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update_User(myUser: MyUser?)

    /**
     * DELETE ____________________________________________________________________________________________
     */
    @Query("DELETE FROM MyUser WHERE id = :id")
    fun delete_User(id: String)
}