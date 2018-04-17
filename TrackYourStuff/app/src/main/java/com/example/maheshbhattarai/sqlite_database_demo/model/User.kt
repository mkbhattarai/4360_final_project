package com.example.maheshbhattarai.sqlite_database_demo.model

/**
 * Created by Siddharth on 01-04-2018.
 */
class User {
    var name: String ? = null
    var email: String ? = null
    var address: String ? = null
    var password: String ? = null
    var phoneno: String ? = null
    var jobType: String ? = null
    var location: String ? = null
    var user_id: String ? = null
    var login_status : String ? =null
    var user_latitude : String ? =null
    var user_longititude : String ? =null
    var job_cat : String ? =null

    override fun toString(): String {
        return "User(name=$name, email=$email, address=$address, password=$password, phoneno=$phoneno, jobType=$jobType, location=$location, user_id=$user_id, login_status=$login_status, user_latitude=$user_latitude, user_longititude=$user_longititude, job_cat=$job_cat)"
    }


    //    private var name: String? = null


}