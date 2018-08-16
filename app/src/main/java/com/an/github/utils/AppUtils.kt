package com.an.github.utils

import com.an.github.model.Repository
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {

    companion object {

        fun getLastWeekDate() : String {

            val startCalendar = Calendar.getInstance()
            startCalendar.time = Date()
            startCalendar.add(Calendar.DAY_OF_YEAR, -7)
            startCalendar.set(Calendar.HOUR_OF_DAY, 0)
            startCalendar.set(Calendar.MINUTE, 0)
            startCalendar.set(Calendar.SECOND, 0)
            startCalendar.set(Calendar.MILLISECOND, 0)
            val date = startCalendar.time

            val df = SimpleDateFormat("YYYY-MM-dd")
            return df.format(date)
        }


        fun getDate(dateString: String): String {

            try {
                val format1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
                val date = format1.parse(dateString)
                val sdf = SimpleDateFormat("MMM d, yyyy")
                return sdf.format(date)
            } catch (ex: Exception) {
                ex.printStackTrace()
                return "xx"
            }
        }


        fun isEmpty(obj: List<Repository>?): Boolean {
            return (obj == null || obj.isEmpty() || obj.size == 0)
        }
    }
}