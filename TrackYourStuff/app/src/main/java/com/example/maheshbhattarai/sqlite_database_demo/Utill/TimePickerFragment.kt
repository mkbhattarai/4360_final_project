package com.example.maheshbhattarai.sqlite_database_demo.Utill

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.maheshbhattarai.sqlite_database_demo.R
import java.util.*

@SuppressLint("ValidFragment")
class TimePickerFragment(editText: EditText) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var calendar: Calendar
    var edt=editText

 var mHours : Int = 0
     var mMin : Int =0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Initialize a Calendar instance
        calendar = Calendar.getInstance()

        // Get the system current hour and minute
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        /*
            *** reference source developer.android.com ***

            TimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener,
            int hourOfDay, int minute, boolean is24HourView)
                Creates a new time picker dialog.

            TimePickerDialog(Context context, int themeResId, TimePickerDialog.OnTimeSetListener
            listener, int hourOfDay, int minute, boolean is24HourView)
                Creates a new time picker dialog with the specified theme.

        */

        // Create a TimePickerDialog with system current time
        // Return the TimePickerDialog
        return TimePickerDialog(
                activity, // Context
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth, // Theme
                this, // TimePickerDialog.OnTimeSetListener
                hour, // Hour of day
                minute, // Minute
                false // Is 24 hour view
        )
//        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

    }


    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the returned time
//

        var mHourString =java.lang.String.valueOf(hourOfDay)
        var mMinString = java.lang.String.valueOf(minute)

        if (hourOfDay < 10 ){
          mHourString="0"+hourOfDay
            "\n${getHourAMPM(mHourString.toInt())}:$minute ${getAMPM(mMinString.toInt())}"
        }else{
            mHourString= hourOfDay.toString()
        }

        if(minute < 10){
            mMinString = "0"+minute
        } else {
            mMinString = minute.toString()

        }
//        var mHourStringIn =java.lang.Integer.parseInt(mHourString)
//        var mMinStringIn = java.lang.Integer.parseInt(mMinString)

        Log.e("Sidd","Hours : "+mHourString + "Miniustes :" +mMinString)


//        var s =
//        edt.setText(s)
    }


    // When user cancel the time picker dialog
    override fun onCancel(dialog: DialogInterface?) {
        Toast.makeText(activity,"Picker Canceled.",Toast.LENGTH_SHORT).show()
        super.onCancel(dialog)
    }


    // Custom method to get AM PM value from provided hour
    private fun getAMPM(hour:Int):String{
        return if(hour>11)"PM" else "AM"
    }


    // Custom method to get hour for AM PM time format
    private fun getHourAMPM(hour:Int):Int{
        // Return the hour value for AM PM time format
        var modifiedHour = if (hour>11)hour-12 else hour
        if (modifiedHour == 0){modifiedHour = 12}
        return modifiedHour
    }
}




