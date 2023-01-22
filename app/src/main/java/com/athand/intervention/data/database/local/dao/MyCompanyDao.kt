package com.athand.intervention.data.database.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.athand.intervention.data.entity.MyCompany

/**
 * Cree le 11/01/2023 par Gassama Souleyman
 */
@Dao
interface MyCompanyDao {
    /**
     * CREATE ____________________________________________________________________________________________
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inset_Company(company: MyCompany?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inset_List_Company(companys: MutableList<MyCompany>?)

    /**
     * GET _______________________________________________________________________________________________
     */
    @Query("SELECT * FROM MyCompany WHERE uidAuthor IN (:userUid)")
    fun get_All_Company(userUid: String): LiveData<MutableList<MyCompany>>?

    /**
     * UPDATE ____________________________________________________________________________________________
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update_Company(company: MyCompany?)

    /**
     * DELETE ____________________________________________________________________________________________
     */
    @Query("DELETE FROM MyCompany WHERE id = :id")
    fun delete_Company(id: String)
}