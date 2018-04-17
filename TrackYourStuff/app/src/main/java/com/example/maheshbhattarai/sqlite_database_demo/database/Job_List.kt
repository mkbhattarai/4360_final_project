package com.example.maheshbhattarai.sqlite_database_demo.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Mahesh Bhattarai on 2/21/2018.
 */
@Entity(tableName = "job_list")
public class Job_List {

    @PrimaryKey(autoGenerate = true)
    var jobId: Long = 0

    @ColumnInfo(name = "jobt_name")
    var jobt_name: String? = null

    @ColumnInfo(name = "customer_name")
    var customer_name: String? = null

    @ColumnInfo(name = "customer_address")
    var customer_address: String? = null

    @ColumnInfo(name = "work_nature")
    var work_nature: String? = null

    @ColumnInfo(name = "estimated_time")
    var estimated_time: String? = null

  /*  @ColumnInfo(name = "amount")
    var amount: String? = null

    @ColumnInfo(name = "apply")
    var apply: String? = null*/

}