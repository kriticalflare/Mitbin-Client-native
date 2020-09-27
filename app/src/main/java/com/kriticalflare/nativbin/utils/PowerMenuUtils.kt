package com.kriticalflare.nativbin.utils


fun String.formatLanguageApi(): String{
    return when (this) {
        "Plain Text" -> {
            this
        }
        "Python" -> {
            "python"
        }
        "Json" -> {
            "json"
        }
        "C Like" -> {
            "clike"
        }
        else -> {
            throw Throwable("Invalid language")
        }
    }
}