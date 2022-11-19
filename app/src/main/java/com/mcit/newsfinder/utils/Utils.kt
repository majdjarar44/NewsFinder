package com.mcit.newsfinder.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getCurrentDay(): String {
        val c = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return df.format(c)
    }
}


