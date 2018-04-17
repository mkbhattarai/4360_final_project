package com.example.maheshbhattarai.sqlite_database_demo.Utill

import android.content.Context

/**
 * Created by Siddharth on 01-04-2018.
 */
class Apprefence {

    companion object {
        var PREFERENCE: String? = "TRACKYOURSTAFF";
        var ROLE: String? = "role";
        var USERID: String? = "user_id";
        var USER_JOB_CAT: String? = "user_job_cat";
        var JOB_ID : String ? ="job_id"
        public fun setRole(mContext: Context, role: String) {
            var settings = mContext.getSharedPreferences(PREFERENCE, 0)
            if (settings != null) {
                var editor = settings.edit()
                editor.putString(ROLE, role)
                editor.commit()
            }
        }

        public fun getRole(mContext: Context): String {
            val pereference = mContext.getSharedPreferences(
                    PREFERENCE, 0)
            return pereference.getString(ROLE, "")
        }

        public fun setUserId(mContext: Context, user_id: String) {
            var settings = mContext.getSharedPreferences(PREFERENCE, 0)
            if (settings != null) {
                var editor = settings.edit()
                editor.putString(USERID, user_id)
                editor.commit()
            }
        }

        public fun getUserId(mContext: Context): String {
            val pereference = mContext.getSharedPreferences(
                    PREFERENCE, 0)
            return pereference.getString(USERID, "")
        }

        public fun setLatitude(mContext: Context, user_id: String) {
            var settings = mContext.getSharedPreferences(PREFERENCE, 0)
            if (settings != null) {
                var editor = settings.edit()
                editor.putString(USERID, user_id)
                editor.commit()
            }
        }

        public fun getLattiude(mContext: Context): String {
            val pereference = mContext.getSharedPreferences(
                    PREFERENCE, 0)
            return pereference.getString(USERID, "")
        }

        public fun setJobcat(mContext: Context, user_id: String) {
            var settings = mContext.getSharedPreferences(PREFERENCE, 0)
            if (settings != null) {
                var editor = settings.edit()
                editor.putString(USER_JOB_CAT, user_id)
                editor.commit()
            }
        }

        public fun getJobCat(mContext: Context): String {
            val pereference = mContext.getSharedPreferences(
                    PREFERENCE, 0)
            return pereference.getString(USER_JOB_CAT, "")
        }

        public fun setJob_Id(mContext: Context, user_id: String) {
            var settings = mContext.getSharedPreferences(PREFERENCE, 0)
            if (settings != null) {
                var editor = settings.edit()
                editor.putString(JOB_ID, user_id)
                editor.commit()
            }
        }

        public fun getJob_id(mContext: Context): String {
            val pereference = mContext.getSharedPreferences(
                    PREFERENCE, 0)
            return pereference.getString(JOB_ID, "")
        }

    }

}