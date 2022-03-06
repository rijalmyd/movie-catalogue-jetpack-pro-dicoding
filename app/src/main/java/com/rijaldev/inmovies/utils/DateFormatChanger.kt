package com.rijaldev.inmovies.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormatChanger {

    @SuppressLint("SimpleDateFormat")
    fun toOtherDate(date: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var newDate: Date? = null

        try {
            newDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (newDate != null) {
            SimpleDateFormat("dd MMM yyyy").format(newDate)
        } else {
            null
        }
    }

    @Suppress("SimpleDateFormat")
    fun toOnlyYear(date: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var newDate: Date? = null

        try {
            newDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (newDate != null) {
            SimpleDateFormat("yyyy").format(newDate)
        } else {
            null
        }
    }
}