package com.tvink28.weather.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeConversion {
    private const val DATE_TIME_FORMAT = "E, dd MMMM, HH:mm"

    fun formattedDateTime(date: Int, timeZoneOffset: Int): String {
        val time = date.toLong() * 1000
//        val time = (date.toLong() * 1000) + (timeZoneOffset.toLong() * 1000)
        val date = Date(time)
//        val timeZone = TimeZone.getDefault()
//        timeZone.rawOffset = timeZoneOffset
        val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }
}