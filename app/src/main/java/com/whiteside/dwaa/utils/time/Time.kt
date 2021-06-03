package com.whiteside.dwaa.utils.time

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

class Time {

    companion object {

        fun convertCalenderSecondsToTimestamp(seconds: Long) = Timestamp(seconds / 1000, 0)

        fun convertTimestampToString(time: Timestamp): String {
            val format = SimpleDateFormat("dd-MM-yyyy")
            return format.format(time.toDate())
        }

        fun timeFromNow(timestamp: Timestamp): String {
            val seconds = Timestamp.now().seconds - timestamp.seconds
            return if (seconds >= TimeConstants.secondeInYear) {
                val years = seconds / TimeConstants.secondeInYear
                "$years years ago"
            } else if (seconds >= TimeConstants.secondeInMonth) {
                val months = seconds / TimeConstants.secondeInMonth
                "$months months ago"
            } else if (seconds >= TimeConstants.secondeInDay) {
                val days = seconds / TimeConstants.secondeInDay
                "$days days ago"
            } else if (seconds >= TimeConstants.secondeInHour) {
                val hours = seconds / TimeConstants.secondeInHour
                "$hours hours ago"
            } else if (seconds >= TimeConstants.secondeInMinute) {
                val minutes = seconds / TimeConstants.secondeInMinute
                "$minutes minutes ago"
            } else {
                "seconds ago"
            }
        }
    }
}