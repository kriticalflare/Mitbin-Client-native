package com.kriticalflare.nativbin.utils



fun TimeInMinutes(duration: String): Int {
    return when (duration) {
        "One day" -> {
            1440
        }
        "One week" -> {
            10080
        }
        "One month" -> {
            // 30 days
            43200
        }
        else -> {
            throw Throwable("Invalid Duration")
        }
    }
}