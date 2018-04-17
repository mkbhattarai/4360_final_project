package com.example.maheshbhattarai.sqlite_database_demo.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Mahesh Bhattarai on 2/21/2018.
 */
@Entity(tableName = "registration")
public class Registration {

    @PrimaryKey(autoGenerate = true)
    var employId: Long = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "email")
    var email: String? = null

    @ColumnInfo(name = "address")
    var address: String? = null

    @ColumnInfo(name = "phone_number")
    var phone_number: String? = null

    @ColumnInfo(name = "password")
    var password: String? = null

    @ColumnInfo(name = "role")
    var role: String? = null


}