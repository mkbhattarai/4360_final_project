package com.example.maheshbhattarai.sqlite_database_demo

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.maheshbhattarai.sqlite_database_demo.adapter.CustomAdapter
import com.example.maheshbhattarai.sqlite_database_demo.database.AppDatabase
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText


class Job_Listing : AppCompatActivity() {

    private var mDb: AppDatabase? = null
    lateinit var work_nature: EditText
    var strings = arrayOf("plumbing", "lighting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_listing)

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        val job_list = mDb?.employDao()?.findAllJobSync()
        Log.e("size", job_list?.size.toString())

        //getting recyclerview from xml
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        work_nature = findViewById<EditText>(R.id.work_nature)

        work_nature.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this@Job_Listing)
            builder.setItems(strings, DialogInterface.OnClickListener { dialog, item ->
                work_nature.setText(strings[item])
                val job_list = mDb?.employDao()?.findSelectJobSync(strings[item])
                Log.e("size", job_list?.size.toString())

                //adding a layoutmanager
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

                //creating our adapter
//                val adapter = CustomAdapter(job_list!!)

                //now adding the adapter to recyclerview
//                recyclerView.adapter = adapter
//                adapter.notifyDataSetChanged()


            })
            val alert = builder.create()
            alert.show()
        })





        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //creating our adapter
//        val adapter = CustomAdapter(job_list!!)
//
//        //now adding the adapter to recyclerview
//        recyclerView.adapter = adapter
//        adapter.notifyDataSetChanged()
//

      /*  recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(this, recyclerView, object : RecyclerItemClickListener.OnItemClickListener() {
                    fun onItemClick(view: View, position: Int) {
                        // do whatever
                    }

                    fun onLongItemClick(view: View, position: Int) {
                        // do whatever
                    }
                })
        )*/




    }
}
