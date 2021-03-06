package com.kriticalflare.bin_wrapper.data

import com.kriticalflare.bin_wrapper.remote.model.Paste
import com.kriticalflare.bin_wrapper.remote.model.UploadPaste

interface Repository {

    suspend fun getPaste(name: String): List<Paste>

    suspend fun addPaste(paste: UploadPaste): PasteResponse
}