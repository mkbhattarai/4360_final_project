package com.example.maheshbhattarai.sqlite_database_demo.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.maheshbhattarai.sqlite_database_demo.R
import com.example.maheshbhattarai.sqlite_database_demo.ReceiptActivity
import com.example.maheshbhattarai.sqlite_database_demo.SubmissionActivity
import com.example.maheshbhattarai.sqlite_database_demo.Utill.AppConstant
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.ViewPraposalActivity
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal

/**
 * Created by Mahesh Bhattarai on 2/23/2018.
 */
class SeekerCustomAdapter(public  var mContext : Context, val userList: List<JobMdal>) : RecyclerView.Adapter<SeekerCustomAdapter.ViewHolder>() , Filterable {

    //var sharedpreferencesID: SharedPreferences? = null
    lateinit var login_type : String
    //this method is returning the view for each item in the list

    var userListFilter : List<JobMdal> =userList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeekerCustomAdapter.ViewHolder {
        /*sharedpreferencesID = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        login_type = sharedpreferencesID?.getString("id", "").toString()
        Log.e("login_type", login_type)*/
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row_data1, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: SeekerCustomAdapter.ViewHolder, position: Int) {
        Log.e("sidd",userList.toString())

        holder.bindItems(userListFilter[position],userListFilter)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userListFilter.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: JobMdal,list : List<JobMdal>) {
            val job_title = itemView.findViewById<TextView>(R.id.job_title)
            val customer_name = itemView.findViewById<TextView>(R.id.customer_name)
            val customer_address = itemView.findViewById<TextView>(R.id.customer_address)
            val work_nature = itemView.findViewById<TextView>(R.id.work_nature)
            val estimatedTime = itemView.findViewById<TextView>(R.id.estimated_time)
            val btn_apply = itemView.findViewById<Button>(R.id.btn_apply)
            val estimated_phoneNo = itemView.findViewById<TextView>(R.id.customer_phone)

           if (user!=null) {

               if (user.jobApproved==AppConstant.COMPLETED){
                   job_title.text = user.jobTitle
                   customer_name.text = user.customerName
                   estimatedTime.text = user.estimated_time
                   customer_address.text = user.customer_address
                   work_nature.text = user.work_nature
                   estimated_phoneNo.text=user.estimated_phoneNo
                   btn_apply.setText("Completed Job")
                   btn_apply.setOnClickListener {
                       val pos = adapterPosition
                       var user=list.get(pos)
                       Log.e("Sidd",user.jobApproved)
                       Apprefence.setJob_Id(itemView.context,user.jobId.toString())
                       itemView.context.startActivity(Intent(itemView.context,ReceiptActivity::class.java))
                       Apprefence.setJobcat(itemView.context,user.jobApproved.toString())
                   }
               }else if (user.jobApproved==AppConstant.PENDING){
                   job_title.text = user.jobTitle
                   customer_name.text = user.customerName
                   estimatedTime.text = user.estimated_time
                   customer_address.text = user.customer_address
                   work_nature.text = user.work_nature
                   btn_apply.setText("Apply Job")

                   btn_apply.setOnClickListener {
                       val pos = adapterPosition
                       var user=list.get(pos)
                       Log.e("Sidd",user.jobApproved)
                       Apprefence.setJob_Id(itemView.context,user.jobId.toString())
                       itemView.context.startActivity(Intent(itemView.context,SubmissionActivity::class.java))
                   }
               }
           }


        }
    }
    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    userListFilter = userList
                } else {
                    val filteredList = ArrayList<JobMdal>()
                    for (row in userListFilter) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.estimated_phoneNo?.toLowerCase()!!.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }

                    userListFilter = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = userListFilter
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                userListFilter = filterResults.values as ArrayList<JobMdal>
                notifyDataSetChanged()
            }
        }
    }

}