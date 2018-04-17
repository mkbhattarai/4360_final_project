package com.example.maheshbhattarai.sqlite_database_demo.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

/**
 * Created by Mahesh Bhattarai on 2/21/2018.
 */
@Dao
public interface RegistratiomDao {

    @Insert(onConflict = REPLACE)
    abstract fun insertEmploy(employee: Registration)

    @Insert(onConflict = REPLACE)
    abstract fun insertJobs(job_list: Job_List)

    @Insert(onConflict = IGNORE)
    fun insertOrReplaceEmploy(employees: Registration)

    @Update(onConflict = REPLACE)
    fun updateEmploy(employee: Registration)

    @Query("DELETE FROM Registration")
    fun deleteAll()

    @Query("SELECT * FROM registration")
    fun findAllEmploySync(): List<Registration>

    @Query("SELECT * FROM job_list")
    fun findAllJobSync(): List<Job_List>

    @Query("SELECT * FROM job_list WHERE work_nature = :name")
    fun findSelectJobSync(name : String): List<Job_List>
}