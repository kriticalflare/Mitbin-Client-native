package com.kriticalflare.bin_wrapper.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UploadPaste(
    @Json(name = "name")
    val name: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "language")
    val language: String,
    @Json(name = "expiresInMinutes")
    val expiresInMinutes: Int
)