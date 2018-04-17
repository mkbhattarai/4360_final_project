package com.example.maheshbhattarai.sqlite_database_demo.adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.maheshbhattarai.sqlite_database_demo.R
import com.example.maheshbhattarai.sqlite_database_demo.ReceiptActivity
import com.example.maheshbhattarai.sqlite_database_demo.UpdateJobActivity
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.ViewPraposalActivity
import com.example.maheshbhattarai.sqlite_database_demo.database.DBHelper
import com.example.maheshbhattarai.sqlite_database_demo.model.JobMdal

/**
 * Created by Mahesh Bhattarai on 2/23/2018.
 */
class CustomAdapter(public  var mContext : Context ,val userList: List<JobMdal>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(), Filterable {

    //var sharedpreferencesID: SharedPreferences? = null
    lateinit var login_type : String
    //this method is returning the view for each item in the list
    var userListFilter : List<JobMdal> =userList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        /*sharedpreferencesID = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        login_type = sharedpreferencesID?.getString("id", "").toString()
        Log.e("login_type", login_type)*/
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row_data, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userListFilter[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userListFilter.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: JobMdal) {
            val job_title = itemView.findViewById<TextView>(R.id.job_title)
            val customer_name = itemView.findViewById<TextView>(R.id.customer_name)
            val customer_address = itemView.findViewById<TextView>(R.id.customer_address)
            val work_nature = itemView.findViewById<TextView>(R.id.work_nature)
            val estimatedTime = itemView.findViewById<TextView>(R.id.estimated_time)
            val customer_phone = itemView.findViewById<TextView>(R.id.customer_phone)
//            val txt_hourly = itemView.findViewById<TextView>(R.id.txt_hourly)
            val img_edt = itemView.findViewById<ImageView>(R.id.img_edt)
            val imgV_delete = itemView.findViewById<ImageView>(R.id.imgV_delete)
            val btn_com = itemView.findViewById<Button>(R.id.btn_com)
           if (user!=null) {
               job_title.text = user.jobTitle
               customer_name.text = user.customerName
               estimatedTime.text = user.estimated_time
               customer_address.text = user.customer_address
               work_nature.text = user.work_nature
               customer_phone.text = user.estimated_phoneNo
//               txt_hourly.text = user.hourly_charhge
           }

            Log.e("CustomAdapter1",Apprefence.getJobCat(itemView.context))
            Log.e("CustomAdapter2",user.jobApproved)
            if (user.jobApproved=="0"){

                img_edt.visibility=View.VISIBLE
                imgV_delete.visibility=View.VISIBLE
                itemView.setOnClickListener {
                    Log.e("CustomAdapter3",Apprefence.getJobCat(itemView.context))
                    Log.e("CustomAdapter4",user.jobApproved.toString())

                    val pos = adapterPosition
                    Toast.makeText(itemView.context, user.jobId.toString(), Toast.LENGTH_SHORT).show()
                    Apprefence.setJob_Id(itemView.context,user.jobId.toString())
                    Apprefence.setJobcat(itemView.context,user.jobApproved.toString())
                    itemView.context.startActivity(Intent(itemView.context,ViewPraposalActivity::class.java).putExtra("job_title",user.jobTitle)
                            .putExtra("estimate_no",user.estimated_phoneNo).putExtra("customer_name",user.customerName)
                            .putExtra("customer_address",user.customer_address).putExtra("worknature",user.work_nature).putExtra("estimated_time",user.estimated_time).putExtra("rxt_hourly_v1",user.hourly_charhge))

                }
                img_edt.setOnClickListener(View.OnClickListener { v: View? ->
                    AlertDialog.Builder(itemView.context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you want to edit this job ? ")
                            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss()
                                Apprefence.setJob_Id(itemView.context,user.jobId.toString())
                                itemView.context.startActivity(Intent(itemView.context,UpdateJobActivity::class.java))
                            })
                            .setNegativeButton("No", null)
                            .show()
                })

                imgV_delete.setOnClickListener(View.OnClickListener { v: View? ->
                       var mDialog = ProgressDialog(itemView.context)
                    mDialog.setMessage("please wait..")
                    mDialog.setCancelable(false)
                    mDialog.setCanceledOnTouchOutside(false)
                    mDialog.show()

                      AlertDialog.Builder(itemView.context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you want to delete this job ? ")
                            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss()
                                var db = DBHelper(itemView.context)
                                var handler = Handler()
                                handler.postDelayed(Runnable {
                                   mDialog.dismiss()
                                    dialog.dismiss()
                                    if (db!=null){
                                    var isDelete =db.deleteJob(user.jobId.toString())
                                    if (isDelete){
                                        Toast.makeText(itemView.context,"delete this job successfully",Toast.LENGTH_LONG).show()
                                    }else{
                                        Toast.makeText(itemView.context,"delete this job unsuccessfully",Toast.LENGTH_LONG).show()

                                    }
                                } },2000)

                            })
                            .setNegativeButton("No", null)
                            .show()


                })



            }else if (user.jobApproved=="2"){
                img_edt.visibility=View.GONE
                imgV_delete.visibility=View.GONE
                Log.e("CustomAdapter5",Apprefence.getJobCat(itemView.context))
                Log.e("CustomAdapter6",user.jobApproved.toString())

/*   txt_job_title1V?.setText(model?.jobTitle)
        txt_job_customer_name?.setText(model?.customerName)
        txt_job_customer_address?.setText(model?.customer_address)
        txt_job_customer_phonn?.setText(model?.estimated_phoneNo)
        txt_short_dis?.setText(model?.job_short_dis)
        txt_job_cat?.setText(model?.work_nature)
        txt_spent_time?.setText(model?.job_spent_time)
        txt_job_hourly_charges?.setText(model?.hourly_charhge)
        txt_job_extra_charges?.setText(model?.job_extra_charge)
        txt_job_distane_charges?.setText(model?.distance_charge)
        txt_job_total_ammount?.setText(model?.total_amount)*/

                itemView.setOnClickListener {
                    val pos = adapterPosition
                    Toast.makeText(itemView.context, user.jobId.toString(), Toast.LENGTH_SHORT).show()
                    Apprefence.setJob_Id(itemView.context,user.jobId.toString())
                    itemView.context.startActivity(Intent(itemView.context,ReceiptActivity::class.java))
                    Apprefence.setJob_Id(itemView.context,user.jobId.toString())
                    Apprefence.setJobcat(itemView.context,user.jobApproved.toString())
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


