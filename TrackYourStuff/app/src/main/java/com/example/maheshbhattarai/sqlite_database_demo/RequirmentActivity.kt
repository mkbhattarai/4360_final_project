package com.example.maheshbhattarai.sqlite_database_demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.maheshbhattarai.sqlite_database_demo.database.AppDatabase
import com.example.maheshbhattarai.sqlite_database_demo.database.Job_List
import java.util.*
import android.content.DialogInterface
import android.content.res.Resources
import android.support.v7.app.AlertDialog


class RequirmentActivity : AppCompatActivity() {

    private var mDb: AppDatabase? = null

    var number1 = 0
    var number2 = 0
    var result: Double = 0.0
    lateinit var job_name: EditText
    lateinit var customer_name: EditText
    lateinit var customer_address: EditText
    lateinit var work_nature: EditText
    lateinit var estimated_time: EditText
    lateinit var btn_submit: Button
    var strings = arrayOf("plumbing", "lighting")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requirment)

        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());


        job_name = findViewById<EditText>(R.id.job_title)
        customer_name = findViewById<EditText>(R.id.customer_name)
        customer_address = findViewById<EditText>(R.id.customer_address)
        work_nature = findViewById<EditText>(R.id.work_nature)
        estimated_time = findViewById<EditText>(R.id.estimated_time)


        btn_submit = findViewById(R.id.btn_submit)
        btn_submit.setOnClickListener(View.OnClickListener {
            submitJob()
        })

        /*start_time.setOnClickListener(View.OnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                start_time.setText(selectedHour.toString() + ":" + selectedMinute)

                number1 = selectedHour;

                getDifference()

            }, hour, minute, false)//Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        })

        end_time.setOnClickListener(View.OnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                end_time.setText(selectedHour.toString() + ":" + selectedMinute)

                number1 = selectedHour;

                getDifference()
            }, hour, minute, false)//Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        })
*/

        work_nature.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this@RequirmentActivity)
            builder.setItems(strings, DialogInterface.OnClickListener { dialog, item ->
                work_nature.setText(strings[item])
            })
            val alert = builder.create()
            alert.show()
        })


    }

    private fun submitJob() {
        if (job_name.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Job Title", Toast.LENGTH_SHORT).show()
        } else if (customer_name.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Customer Name", Toast.LENGTH_SHORT).show()
        } else if (customer_address.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Customer Address", Toast.LENGTH_SHORT).show()
        } else if (work_nature.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Work Nature", Toast.LENGTH_SHORT).show()
        } else if (estimated_time.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Estimated Title", Toast.LENGTH_SHORT).show()
        } else {

            val job_list = Job_List()
            job_list.jobt_name = job_name.text.toString()
            job_list.customer_name = customer_name.text.toString()
            job_list.customer_address = customer_address.text.toString()
            job_list.work_nature = work_nature.text.toString()
            job_list.estimated_time = estimated_time.text.toString()

            mDb?.employDao()?.insertJobs(job_list)
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
            job_name.setText("")
            customer_name.setText("")
            customer_address.setText("")
            work_nature.setText("")
            estimated_time.setText("")
            job_name.requestFocus()

        }
    }

    fun  onSubmit(view: View){

    }
   /* private fun getDifference() {
        if (number1 != null && number2 != null) {

            result = (number1 - number2).toDouble()
            println("number1 - number2 = $result")
            val hours = Math.abs(result.toInt()).toString()
            Log.e("result", Math.abs(result.toInt()).toString())
            estimated_time.setText(hours)

        }
    }
*/
}
