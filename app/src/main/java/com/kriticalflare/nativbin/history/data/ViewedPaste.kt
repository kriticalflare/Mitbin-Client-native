package com.kriticalflare.nativbin.history.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ViewedPaste(
    @PrimaryKey
    val name: String,
    val content: String,
    val language: String,
    val createdAt: String,
    val expiresAt: String
)