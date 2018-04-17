package com.example.maheshbhattarai.sqlite_database_demo.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.maheshbhattarai.sqlite_database_demo.Utill.AppConstant.Companion.DATABASE_NAME
import com.example.maheshbhattarai.sqlite_database_demo.Utill.AppConstant.Companion.DATABASE_VERSION
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal
import com.example.maheshbhattarai.sqlite_database_demo.model.User
import java.util.ArrayList

/**
 * Created by Mahesh on 04-01-2018.
 */
class DBHelper(mCotext: Context) : SQLiteOpenHelper(mCotext, DATABASE_NAME, null, DATABASE_VERSION!!) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_EMPLOYER_TABLE)
//        db?.execSQL(CREATE_JOBSEEKER_TABLE)
        db.execSQL(CREATE_JOBADD_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        /*EMPLOYER_TABLE*/
        var USER_TABLE: String = "employer_table"
        var USER_NAME: String = "employer_name"
        var USER_EMAIL_ID: String = "employer_email_id"
        var USER_ADDRESS: String = "employer_address"
        var USER_PHONE_NO: String = "employer_phone_no"
        var USER_PASSWORD: String = "employer_password"
        var USER_ROLE: String = "employer_role"
        var USER_UID: String = "employer_uid"
        var USER_LOCATION: String = "employer_location"
        var USER_LOGIN_STATUS: String = "login_status"
        var USER_LATITUDE = "user_latitude"
        var USER_LONGITITUDE = "user_longititude"
        var USER_JOB_CAT = "user_job_cat"


        /*JOB ADD */

        var JOB_ID: String = "job_id"
        var JOB_TABLE: String = "job_table"
        var JOB_TITLE: String = "job_title"
        var JOB_CUSTOMER_NAME: String = "job_customer_name"
        var JOB_CUSTOMER_ADDRESS: String = "job_customer_address"
        var JOB_WORK_NATURE: String = "job_work_nature"
        var JOB_ESTIMATE_TIME: String = "job_estimate_time";
        var JOB_APPROVED: String = "job_approved"
        var JOB_COMPLETED: String = "job_completed"
        var JOB_APPLIED: String = "job_applied"
        var JOB_PHONE_NUMBER: String = "job_phone_number"
        var CUSTOMER_LATITUDE: String = "customer_latitude"
        var CUSTOMER_LONGITIUDE: String = "customer_longitiude"
        var JOB_DATEANDTIME: String = "job_dateandtime"
        var JOB_WEEKLY = "job_weekly"
        var JOB_MONTHLY = "job_monthly"
        var JOB_CREATED_BY = "job_created_by"

        var JOB_SPENT_TIME = "job_spent_time"
        var JOB_EXTRA_CHARGE = "job_extra_charge"
        var JOB_SHORT_DIS = "job_short_dis"
        var HOURLY_CHARHGE="hourlyCharge"
        var DISTANCE_CHARGE="distanceCharage"
        var TOTAL_AMOUNT="totalAmmount"


        var JOBSEEKER_TABLE: String = "jobseeker_table"
        var JOBSEEKER_NAME: String = "jobseeker_name"
        var JOBSEEKER_EMAIL_ID: String = "jobseeker_email_id"
        var JOBSEEKER_ADDRESS: String = "jobseeker_address"
        var JOBSEEKER_PHONE_NO: String = "jobseeker_phone_no"
        var JOBSEEKER_PASSWORD: String = "jobseeker_password"
        var JOBSEEKER_ROLE: String = "jobseeker_role"
        var JOBSEEKER_UID: String = "jobseeker_uid"
        var JOBSEEKER_LOCATION: String = "jobseeker_location"

        private val CREATE_EMPLOYER_TABLE =
                "CREATE TABLE " + USER_TABLE + " (" +
                        USER_UID + " TEXT PRIMARY KEY," +
                        USER_NAME + " TEXT," +
                        USER_EMAIL_ID + " TEXT," + USER_ADDRESS + " TEXT," + USER_PHONE_NO + " TEXT," + USER_PASSWORD + " TEXT," + USER_LOCATION + " TEXT," + USER_ROLE + " TEXT," + USER_LOGIN_STATUS + " TEXT," + USER_LATITUDE + " TEXT," + USER_LONGITITUDE + " TEXT," + USER_JOB_CAT + " TEXT)"

        private val EMPLOYER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE


        private val CREATE_JOBSEEKER_TABLE =
                "CREATE TABLE " + JOBSEEKER_TABLE + " (" +
                        JOBSEEKER_UID + " TEXT PRIMARY KEY," +
                        JOBSEEKER_NAME + " TEXT," +
                        JOBSEEKER_EMAIL_ID + " TEXT," + JOBSEEKER_ADDRESS + " TEXT," + JOBSEEKER_PHONE_NO + " TEXT," + JOBSEEKER_PASSWORD + " TEXT," + JOBSEEKER_LOCATION + " TEXT," + JOBSEEKER_ROLE + " TEXT)"

        private val JOBSEEKER_TABLE_DROP = "DROP TABLE IF EXISTS " + JOBSEEKER_TABLE
        /* var JOB_PHONE_NUMBER: String = "job_phone_number"
var CUSTOMER_LATITUDE: String = "customer_latitude"
var CUSTOMER_LONGITIUDE: String = "customer_longitiude"
var JOB_DATEANDTIME : String ="job_dateandtime"
*/
        private val CREATE_JOBADD_TABLE =
                "CREATE TABLE " + JOB_TABLE + " (" +
                        JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        JOB_TITLE + " TEXT," +
                        JOB_CUSTOMER_NAME + " TEXT," + JOB_CUSTOMER_ADDRESS + " TEXT," + JOB_APPLIED + " TEXT," + JOB_APPROVED + " TEXT," + JOB_COMPLETED + " TEXT," + JOB_WORK_NATURE + " TEXT," + JOB_ESTIMATE_TIME + " TEXT," + JOB_PHONE_NUMBER + " TEXT," + CUSTOMER_LATITUDE + " TEXT," + CUSTOMER_LONGITIUDE + " TEXT," + JOB_DATEANDTIME + " TEXT," + JOB_WEEKLY + " TEXT," + JOB_MONTHLY + " TEXT," + JOB_CREATED_BY + " TEXT," + JOB_SPENT_TIME + " TEXT," + JOB_EXTRA_CHARGE + " TEXT," + JOB_SHORT_DIS + " TEXT," + HOURLY_CHARHGE + " TEXT," + DISTANCE_CHARGE + " TEXT," + TOTAL_AMOUNT + " TEXT)"

        private val JOBADD_TABLE_DROP = "DROP TABLE IF EXISTS " + JOB_TABLE

    }

    fun user(user: User): Boolean {
        var userContentValue = ContentValues()
        userContentValue.put(USER_NAME, user.name)
        userContentValue.put(USER_EMAIL_ID, user.email)
        userContentValue.put(USER_ADDRESS, user.address)
        userContentValue.put(USER_PASSWORD, user.password)
        userContentValue.put(USER_LOCATION, user.location)
        userContentValue.put(USER_PHONE_NO, user.phoneno)
        userContentValue.put(USER_ROLE, user.jobType)
        userContentValue.put(USER_LATITUDE, user.user_latitude)
        userContentValue.put(USER_LONGITITUDE, user.user_longititude)
        userContentValue.put(USER_JOB_CAT, user.job_cat)
        Log.e("Sid", userContentValue.toString())
        var db = writableDatabase;
        var isInsert: Boolean = db.insert(USER_TABLE, null, userContentValue) > 0
        return isInsert
    }

    fun getUserInfo(email: String, userType: String): User {
//        Log.e("sid",userStatus)
        var list = ArrayList<User>()
        var db = readableDatabase

        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        cursor = db.rawQuery("select * from " + USER_TABLE + " WHERE " + USER_EMAIL_ID + "='" + email + "'" + " AND " + USER_ROLE + "='" + userType + "'", null)


//        cursor = db.rawQuery("SELECT * FORM " + USER_TABLE + " WHERE " + USER_ROLE + "='" + userStatus  + "'", null)
        Log.e("Sidd", cursor.toString())
//        try {
//        } catch (e: SQLiteException) {
//            // if table not yet present, create it
//            db.execSQL(CREATE_EMPLOYER_TABLE)
//            return ArrayList()
//        }
//
//        cursor = db.rawQuery("select * from " + USER_TABLE + " WHERE "+ USER_ROLE + "='" + userStatus + "'",null)

//
////        try {
////            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_USER_ID + "='" + userid + "'", null)
////        } catch (e: SQLiteException) {
////            // if table not yet present, create it
////            db.execSQL(SQL_CREATE_ENTRIES)
////            return ArrayList()
////        }
//
//        try {
//                 } catch (e: SQLiteException) {
//            // if table not yet present, create it
//            db.execSQL(CREATE_EMPLOYER_TABLE)
//
////            db.execSQL(SQL_CREATE_ENTRIES)////
//            return ArrayList()
//        }
        var user = User()
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                user.user_id = cursor.getString(cursor.getColumnIndex(USER_UID))
                user.name = cursor.getString(cursor.getColumnIndex(USER_NAME))
                user.email = cursor.getString(cursor.getColumnIndex(USER_EMAIL_ID))
                user.login_status = cursor.getString(cursor.getColumnIndex(USER_LOGIN_STATUS))
                user.address = cursor.getString(cursor.getColumnIndex(USER_ADDRESS))
                user.password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD))
                user.location = cursor.getString(cursor.getColumnIndex(USER_LOCATION))
                user.phoneno = cursor.getString(cursor.getColumnIndex(USER_PHONE_NO))
                user.jobType = cursor.getString(cursor.getColumnIndex(USER_ROLE))
                user.user_latitude = cursor.getString(cursor.getColumnIndex(USER_LATITUDE))
                user.user_longititude = cursor.getString(cursor.getColumnIndex(USER_LONGITITUDE))
                user.job_cat = cursor.getString(cursor.getColumnIndex(USER_JOB_CAT))
                cursor.moveToNext()
            }
        }
        return user;

    }

    /*
            var JOB_ID: String = "job_id"
            var JOB_TABLE: String = "job_table"
            var JOB_TITLE: String = "job_title"
            var JOB_CUSTOMER_NAME: String = "job_customer_name"
            var JOB_CUSTOMER_ADDRESS: String = "job_customer_address"
            var JOB_WORK_NATURE: String = "job_work_nature"
            var JOB_ESTIMATE_TIME: String = "job_estimate_time";
            var JOB_APPROVED: String = "job_approved"
            var JOB_COMPLETED: String = "job_completed"
            var JOB_APPLIED: String = "job_applied"
            var JOB_PHONE_NUMBER: String = "job_phone_number"
            var CUSTOMER_LATITUDE: String = "customer_latitude"
             var CUSTOMER_LONGITIUDE: String = "customer_longitiude"*/

    fun postJob(mJob: JobMdal): Boolean {
        var jobContentValues = ContentValues()
        jobContentValues.put(JOB_TITLE, mJob.jobTitle)
        jobContentValues.put(JOB_CUSTOMER_NAME, mJob.customerName)
        jobContentValues.put(JOB_CUSTOMER_ADDRESS, mJob.customer_address)
        jobContentValues.put(JOB_PHONE_NUMBER, mJob.estimated_phoneNo)
        jobContentValues.put(CUSTOMER_LATITUDE, mJob.customer_lattitude)
        jobContentValues.put(CUSTOMER_LONGITIUDE, mJob.customer_logitiude)
        jobContentValues.put(JOB_WORK_NATURE, mJob.work_nature)
        jobContentValues.put(JOB_ESTIMATE_TIME, mJob.estimated_time)
        jobContentValues.put(JOB_APPROVED, mJob.jobApproved)
        jobContentValues.put(JOB_APPLIED, mJob.jobApplied)
        jobContentValues.put(JOB_COMPLETED, mJob.jobCompleted)
        jobContentValues.put(JOB_DATEANDTIME, mJob.jobDateAndTime)
        jobContentValues.put(JOB_WEEKLY, mJob.jobWeekly)
        jobContentValues.put(JOB_MONTHLY, mJob.jobMonthly)
        jobContentValues.put(HOURLY_CHARHGE, mJob.hourly_charhge)
        jobContentValues.put(DISTANCE_CHARGE, mJob.distance_charge)
        jobContentValues.put(JOB_EXTRA_CHARGE, mJob.job_extra_charge)
        jobContentValues.put(JOB_SPENT_TIME, mJob.job_spent_time)
        jobContentValues.put(JOB_SHORT_DIS, mJob.job_short_dis)
        jobContentValues.put(TOTAL_AMOUNT, mJob.total_amount)
        jobContentValues.put(JOB_CREATED_BY, mJob.job_created_by)
        var db = writableDatabase
        var isInsert: Boolean = db.insert(JOB_TABLE, null, jobContentValues) > 0
        return isInsert
    }

    fun getAllEmJob(emailId: String): ArrayList<JobMdal> {
        var list = ArrayList<JobMdal>()

        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_CREATED_BY + "='" + emailId + "'", null)


        if (cursor!!.moveToFirst()) {
            list.clear()
            while (cursor.isAfterLast == false) {
     /*   jobContentValues.put(HOURLY_CHARHGE, mJob.hourly_charhge)
        jobContentValues.put(DISTANCE_CHARGE, mJob.distance_charge)
        jobContentValues.put(JOB_EXTRA_CHARGE, mJob.job_extra_charge)
        jobContentValues.put(JOB_SPENT_TIME, mJob.job_spent_time)
        jobContentValues.put(JOB_SHORT_DIS, mJob.job_short_dis)
        jobContentValues.put(TOTAL_AMOUNT, mJob.total_amount)*/
                var mJob = JobMdal()
                mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
                mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
                mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
                mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
                mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
                mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
                mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
                mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
                mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
                mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
                mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
                mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
                mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
                mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
                mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
                mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))
                mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
                mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
                mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
                mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
                mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
                mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))
                list.add(mJob)
                cursor.moveToNext()
            }
        }
        return list; }

//    public fun selectedJob(status1 : String ,status: String,user_cat: String) : ArrayList<JobMdal> {
//        Log.e("Sidd",user_cat)
//        var list = ArrayList<JobMdal>()
//        var db = readableDatabase
//        var cursor: Cursor? = null
//
////        db.select("User", "name")
////                .whereArgs("(_id > {userId}) and (name = {userName})",
////                        "userName" to "John",
////                        "userId" to 42)
//        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'" + " AND " + JOB_APPROVED + "='" + status1 + "'" + " AND " + JOB_APPROVED + "='" + status + "'", null)
//
//        if (cursor!!.moveToFirst()) {
//            list.clear()
//            while (cursor.isAfterLast == false) {
//                var mJob = JobMdal()
//                mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
//                mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
//                mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
//                mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
//                mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
//                mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
//                mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
//                mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
//                mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
//                mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
//                mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
//                mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
//                mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
//                mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
//                mJob.jobYearly = cursor.getString(cursor.getColumnIndex(JOB_YEARLY))
//
//                list.add(mJob)
//                Log.e("Sidd",list.toString())
//                cursor.moveToNext()
//            }
//        }
//        return list;
//
//    }


    fun getAllJob(user_cat: String): ArrayList<JobMdal> {
        Log.e("JOBCAT", user_cat)
        var list = ArrayList<JobMdal>()

        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'", null)


        if (cursor!!.moveToFirst()) {
            list.clear()
            while (cursor.isAfterLast == false) {
                var mJob = JobMdal()
                mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
                mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
                mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
                mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
                mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
                mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
                mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
                mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
                mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
                mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
                mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
                mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
                mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
                mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
                mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
                mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))
                mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
                mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
                mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
                mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
                mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
                mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))
                list.add(mJob)
                cursor.moveToNext()
            }
        }
        return list; }

    public fun selectedJob(user_cat: String,  status1: String): ArrayList<JobMdal> {
        Log.e("Sidd", user_cat)
        var list = ArrayList<JobMdal>()
        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'" + " AND " + JOB_APPROVED + "='" + status1 + "'", null)

        if (cursor!!.moveToFirst()) {
            list.clear()
            while (cursor.isAfterLast == false) {
                var mJob = JobMdal()
                mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
                mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
                mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
                mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
                mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
                mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
                mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
                mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
                mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
                mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
                mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
                mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
                mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
                mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
                mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
                mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))
                mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
                mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
                mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
                mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
                mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
                mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))
                list.add(mJob)
                Log.e("Sidd", list.toString())
                cursor.moveToNext()
            }
        }
        return list;

    }

    public fun getPost(status: String, user_cat: String): ArrayList<JobMdal> {
        var list = ArrayList<JobMdal>()
        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'" + " AND " + JOB_APPROVED + "='" + status + "'", null)

        if (cursor!!.moveToFirst()) {
            list.clear()
            while (cursor.isAfterLast == false) {
                var mJob = JobMdal()
                mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
                mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
                mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
                mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
                mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
                mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
                mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
                mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
                mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
                mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
                mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
                mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
                mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
                mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
                mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
                mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))
                mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
                mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
                mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
                mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
                mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
                mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))
                list.add(mJob)
                Log.e("Sidd", list.toString())
                cursor.moveToNext()
            }
        }
        return list;
    }

    public fun updateStatus(id: String, model: JobMdal): Boolean {
        var db = writableDatabase
        val cv = ContentValues()
        cv.put(JOB_TITLE, model.jobTitle) //These Fields should be your String values of actual column names
        cv.put(JOB_CUSTOMER_NAME, model.customerName) //These Fields should be your String values of actual column names
        cv.put(JOB_CUSTOMER_ADDRESS, model.customer_address) //These Fields should be your String values of actual column names
        cv.put(CUSTOMER_LATITUDE, model.customer_lattitude) //These Fields should be your String values of actual column names
        cv.put(CUSTOMER_LONGITIUDE, model.customer_logitiude) //These Fields should be your String values of actual column names
        cv.put(JOB_SPENT_TIME, model.job_spent_time) //These Fields should be your String values of actual column names
        cv.put(JOB_APPROVED, model.jobApproved) //These Fields should be your String values of actual column names
        cv.put(JOB_SHORT_DIS, model.job_short_dis) //These Fields should be your String values of actual column names
        cv.put(JOB_EXTRA_CHARGE, model.job_extra_charge) //These Fields should be your String values of actual column names
        cv.put(JOB_WORK_NATURE, model.work_nature) //These Fields should be your String values of actual column names
        cv.put(JOB_PHONE_NUMBER, model.estimated_phoneNo)
        cv.put(HOURLY_CHARHGE, model.hourly_charhge)
        cv.put(DISTANCE_CHARGE, model.distance_charge)
        cv.put(JOB_SPENT_TIME, model.job_spent_time)
        cv.put(TOTAL_AMOUNT, model.total_amount)


        //These Fields should be your String values of actual column names
        var isUpdateStatus = db.update(JOB_TABLE, cv, "job_id=" + id, null) > 0
        return isUpdateStatus
    }



    fun udpateJobE(job :String ,mJob: JobMdal): Boolean {
        var jobContentValues = ContentValues()
        jobContentValues.put(JOB_TITLE, mJob.jobTitle)
        jobContentValues.put(JOB_CUSTOMER_NAME, mJob.customerName)
        jobContentValues.put(JOB_CUSTOMER_ADDRESS, mJob.customer_address)
        jobContentValues.put(JOB_PHONE_NUMBER, mJob.estimated_phoneNo)
        jobContentValues.put(CUSTOMER_LATITUDE, mJob.customer_lattitude)
        jobContentValues.put(CUSTOMER_LONGITIUDE, mJob.customer_logitiude)
        jobContentValues.put(JOB_WORK_NATURE, mJob.work_nature)
        jobContentValues.put(JOB_ESTIMATE_TIME, mJob.estimated_time)
        jobContentValues.put(JOB_DATEANDTIME, mJob.jobDateAndTime)
        jobContentValues.put(JOB_CREATED_BY, mJob.job_created_by)
        var db = writableDatabase
        var isUpdate: Boolean = db.update(JOB_TABLE, jobContentValues,"job_id=" + job, null) > 0
        return isUpdate
    }
    public fun weekly(user_cat: String,emailId: String): ArrayList<JobMdal> {
        var list = ArrayList<JobMdal>()
        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        /* "' < 'DATE_SUB(NOW(), INTERVAL 7 DAY)'"*/
//         cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'> date('now','-6 day')'", null)
        /* strftime('%s','now', 'localtime') - strftime('%s', entrydate) FROM entities;*/
//
//        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'" + " AND " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'>= DATE_SUB(NOW(),1, DAY)'" +
//                "", null)

        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_CREATED_BY + "='" + emailId + "'" + " AND " + JOB_APPROVED + "='" + user_cat + "'" + " AND " + JOB_DATEANDTIME + " > datetime('now','-6 day')"
                , null)


        Log.e("SiddCur", cursor.toString())

        if (cursor!!.moveToFirst()) {
            var mJob = JobMdal()
            mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
            mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
            mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
            mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
            mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
            mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
            mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
            mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
            mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
            mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
            mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
            mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
            mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
            mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
            mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
            mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))

            mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
            mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
            mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
            mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
            mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
            mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))
            list.add(mJob)
            Log.e("Sidd", list.toString())
            cursor.moveToNext()
        }
        return list;

    }

    public fun monthly(user_cat: String, weekDay: String): ArrayList<JobMdal> {
        var list = ArrayList<JobMdal>()
        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        /* "' < 'DATE_SUB(NOW(), INTERVAL 7 DAY)'"*/
//         cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'> date('now','-6 day')'", null)
        /* strftime('%s','now', 'localtime') - strftime('%s', entrydate) FROM entities;*/
//
//        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'" + " AND " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'>= DATE_SUB(NOW(),1, DAY)'" +
//                "", null)

        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_CREATED_BY + "='" + user_cat + "'" + " AND " + JOB_APPROVED + "='" + weekDay + "'" + " AND " + JOB_DATEANDTIME + " > datetime('now','-29 day')"
                , null)

        Log.e("SiddCur", cursor.toString())

        if (cursor!!.moveToFirst()) {
            var mJob = JobMdal()
            mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
            mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
            mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
            mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
            mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
            mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
            mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
            mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
            mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
            mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
            mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
            mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
            mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
            mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
            mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
            mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))
            mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
            mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
            mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
            mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
            mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
            mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))

            list.add(mJob)
            Log.e("Sidd", list.toString())
            cursor.moveToNext()
        }
        return list;

    }

    public fun yearly(user_cat: String, weekDay: String): ArrayList<JobMdal> {
        var list = ArrayList<JobMdal>()
        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        /* "' < 'DATE_SUB(NOW(), INTERVAL 7 DAY)'"*/
//         cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'> date('now','-6 day')'", null)
        /* strftime('%s','now', 'localtime') - strftime('%s', entrydate) FROM entities;*/
//
//        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'" + " AND " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'>= DATE_SUB(NOW(),1, DAY)'" +
//                "", null)

        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_CREATED_BY + "='" + user_cat + "'" + " AND " + JOB_APPROVED + "='" + weekDay + "'" + " AND " + JOB_DATEANDTIME + " > datetime('now','-365 day')"
                , null)

        Log.e("SiddCur", cursor.toString())

        if (cursor!!.moveToFirst()) {
            var mJob = JobMdal()
            mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
            mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
            mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
            mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
            mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
            mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
            mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
            mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
            mJob.jobApplied = cursor.getString(cursor.getColumnIndex(JOB_APPLIED))
            mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
            mJob.jobCompleted = cursor.getString(cursor.getColumnIndex(JOB_COMPLETED))
            mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
            mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
            mJob.jobWeekly = cursor.getString(cursor.getColumnIndex(JOB_WEEKLY))
            mJob.jobMonthly = cursor.getString(cursor.getColumnIndex(JOB_MONTHLY))
            mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))
            mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
            mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
            mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
            mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
            mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
            mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))

            list.add(mJob)
            Log.e("Sidd", list.toString())
            cursor.moveToNext()
        }
        return list;

    }

    public fun deleteJob(id: String): Boolean {
        var db = writableDatabase
        return db.delete(JOB_TABLE, JOB_ID + "=?", arrayOf(id)) > 0
    }


    public fun singleJob(cat : String, id: String): JobMdal {
        Log.e("Job ",cat)
        Log.e("Job ",id)
        var list = ArrayList<JobMdal>()
        var db = readableDatabase
        var cursor: Cursor? = null

//        db.select("User", "name")
//                .whereArgs("(_id > {userId}) and (name = {userName})",
//                        "userName" to "John",
//                        "userId" to 42)
        /* "' < 'DATE_SUB(NOW(), INTERVAL 7 DAY)'"*/
//         cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'> date('now','-6 day')'", null)
        /* strftime('%s','now', 'localtime') - strftime('%s', entrydate) FROM entities;*/
//
//        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_WORK_NATURE + "='" + user_cat + "'" + " AND " + JOB_DATEANDTIME + "='" + weekDay + "'" +"'>= DATE_SUB(NOW(),1, DAY)'" +
//                "", null)

        cursor = db.rawQuery("select * from " + JOB_TABLE + " WHERE " + JOB_APPROVED + "='" + cat + "'" + " AND " + JOB_ID + "='" + id + "'"
                , null)

        Log.e("SiddCur", cursor.toString())
        var mJob = JobMdal()

        if (cursor!!.moveToFirst()) {
            mJob.jobId = cursor.getString(cursor.getColumnIndex(JOB_ID))
            mJob.jobTitle = cursor.getString(cursor.getColumnIndex(JOB_TITLE))
            mJob.customerName = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_NAME))
            mJob.customer_address = cursor.getString(cursor.getColumnIndex(JOB_CUSTOMER_ADDRESS))
            mJob.customer_lattitude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LATITUDE))
            mJob.customer_logitiude = cursor.getString(cursor.getColumnIndex(CUSTOMER_LONGITIUDE))
            mJob.estimated_phoneNo = cursor.getString(cursor.getColumnIndex(JOB_PHONE_NUMBER))
            mJob.work_nature = cursor.getString(cursor.getColumnIndex(JOB_WORK_NATURE))
            mJob.jobApproved = cursor.getString(cursor.getColumnIndex(JOB_APPROVED))
            mJob.estimated_time = cursor.getString(cursor.getColumnIndex(JOB_ESTIMATE_TIME))
            mJob.jobDateAndTime = cursor.getString(cursor.getColumnIndex(JOB_DATEANDTIME))
            mJob.job_created_by = cursor.getString(cursor.getColumnIndex(JOB_CREATED_BY))
            mJob.hourly_charhge = cursor.getString(cursor.getColumnIndex(HOURLY_CHARHGE))
            mJob.distance_charge = cursor.getString(cursor.getColumnIndex(DISTANCE_CHARGE))
            mJob.job_extra_charge = cursor.getString(cursor.getColumnIndex(JOB_EXTRA_CHARGE))
            mJob.job_spent_time = cursor.getString(cursor.getColumnIndex(JOB_SPENT_TIME))
            mJob.job_short_dis = cursor.getString(cursor.getColumnIndex(JOB_SHORT_DIS))
            mJob.total_amount = cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT))

//            list.add(mJob)
//            Log.e("Sidd", list.toString())
            cursor.moveToNext()
        }
        return mJob;

    }

}