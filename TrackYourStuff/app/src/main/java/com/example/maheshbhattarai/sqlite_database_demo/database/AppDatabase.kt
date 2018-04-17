package com.example.maheshbhattarai.sqlite_database_demo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by Mahesh Bhattarai on 2/21/2018.
 */
@Database(entities = arrayOf(Registration::class,Job_List::class), version = 3)
abstract class AppDatabase : RoomDatabase() {


    abstract fun employDao(): RegistratiomDao

    companion object {

        private var INSTANCE: AppDatabase? = null


        fun getInMemoryDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase::class.java,"simple.db")
                        // To simplify the codelab, allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}