package com.example.maheshbhattarai.sqlite_database_demo.model

/**
 * Created by Siddharth on 02-04-2018.
 */
class JobMdal {


    var jobTitle : String ? =null
    var customerName : String ? =null
    var customer_address : String ? =null
    var estimated_phoneNo : String ? =null
    var work_nature : String ? = null
    var estimated_time : String ? =null
    var jobId :String ? =null
    var jobApproved :String ?=null
    var jobCompleted : String ? =null
    var jobApplied : String ? =null
    var jobCancel : String ? =null
    var customer_lattitude : String ? =null
    var customer_logitiude : String ? =null
    var jobDateAndTime : String ? =null
    var jobWeekly : String ? =null
    var jobMonthly : String ? =null
    var jobYearly : String ? =null
    var job_created_by : String ? =null

    var job_spent_time : String ? =null
    var job_extra_charge : String ? =null
    var job_short_dis : String ? =null


    var hourly_charhge : String ? =null

    var distance_charge : String ?=null

    var total_amount : String ? =null

    override fun toString(): String {
        return "JobMdal(jobTitle=$jobTitle, customerName=$customerName, customer_address=$customer_address, estimated_phoneNo=$estimated_phoneNo, work_nature=$work_nature, estimated_time=$estimated_time, jobId=$jobId, jobApproved=$jobApproved, jobCompleted=$jobCompleted, jobApplied=$jobApplied, jobCancel=$jobCancel, customer_lattitude=$customer_lattitude, customer_logitiude=$customer_logitiude, jobDateAndTime=$jobDateAndTime, jobWeekly=$jobWeekly, jobMonthly=$jobMonthly, jobYearly=$jobYearly, job_created_by=$job_created_by, job_spent_time=$job_spent_time, job_extra_charge=$job_extra_charge, job_short_dis=$job_short_dis, hourly_charhge=$hourly_charhge, distance_charge=$distance_charge, total_amount=$total_amount)"
    }


}