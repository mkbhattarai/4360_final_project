package com.example.maheshbhattarai.sqlite_database_demo.Utill

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Siddharth on 03-04-2018.
 */
class Function {

    companion object {
        private val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        public fun currentDate() : String {
        val cal = Calendar.getInstance()
        return  sdf.format(cal.time)

    }

        public fun currenWeekly(myDate1 : String) : String {
            val cal = Calendar.getInstance()
            val calendar = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            var  myDate = sdf.parse(myDate1);
            calendar.time = myDate
            for (i in 0..6) {
                Log.e("dateTag", sdf.format(cal.time))
                cal.add(Calendar.DAY_OF_WEEK, 1)
            }
            val newDate = calendar.time
            return  sdf.format(newDate)
        }


        public fun currenMonthly() : String {
            val cal = Calendar.getInstance()
            return  sdf.format(cal.get(Calendar.DAY_OF_MONTH))
        }

        public fun currentYearly() : String {
            val cal = Calendar.getInstance()
            return  sdf.format(cal.get(Calendar.DAY_OF_YEAR))
        }
    }
//
}