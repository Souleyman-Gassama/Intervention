package com.athand.intervention.data.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.athand.intervention.data.database.local.dao.MyCompanyDao
import com.athand.intervention.data.database.local.dao.MyUserDao
import com.athand.intervention.data.entity.MyCompany
import com.athand.intervention.data.entity.MyUser
/**
 * Cree le 11/01/2023 par Gassama Souleyman
 */
@Database(
    entities = [MyUser::class, MyCompany::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    /**
     * DAO ___________________________________________________________________________________________
     */
    abstract fun myUserDao(): MyUserDao?
    abstract fun myCompanyDao(): MyCompanyDao?


    companion object {
        /**
         * SINGLETON _____________________________________________________________________________________
         */
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        /**
         * INIT SINGLETON ________________________________________________________________________________
         */
        fun get_INSTANCE(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                synchronized(LocalDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            LocalDatabase::class.java, "MyDatabase.db"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE
        }
    }
}