package com.harera.dwaa.utils.time

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

class Time {

    companion object {

        fun convertCalenderSecondsToTimestamp(seconds: Long) = Timestamp(seconds / 1000, 0)

        fun convertTimestampToString(time: Timestamp): String {
            val format = SimpleDateFormat("dd-MM-yyyy")
            return format.format(time.toDate())
        }

        fun timeFromNowInString(timestamp: Timestamp): String {
            val seconds = Timestamp.now().seconds - timestamp.seconds
            return if (seconds >= TimeConstants.secondeInYear) {
                val years = seconds / TimeConstants.secondeInYear
                "منذ ".plus(years).plus(" سنوات")
            } else if (seconds >= TimeConstants.secondeInMonth) {
                val months = seconds / TimeConstants.secondeInMonth
                "منذ ".plus(months).plus(" شهور ")
            } else if (seconds >= TimeConstants.secondeInDay) {
                val days = seconds / TimeConstants.secondeInDay
                "منذ ".plus(days).plus(" ايام ")
            } else if (seconds >= TimeConstants.secondeInHour) {
                val hours = seconds / TimeConstants.secondeInHour
                "منذ ".plus(hours).plus(" ساعات")
            } else if (seconds >= TimeConstants.secondeInMinute) {
                val minutes = seconds / TimeConstants.secondeInMinute
                "منذ ".plus(minutes).plus(" دقائق")
            } else {
                "منذ ثوان"
            }
        }
    }
}