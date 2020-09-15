package com.kriticalflare.bin_wrapper.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Paste(
    @Json(name = "name")
    val name: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "language")
    val language: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "expiresAt")
    val expiresAt: String
)