package com.kriticalflare.bin_wrapper.remote

import com.kriticalflare.bin_wrapper.remote.model.Paste
import com.kriticalflare.bin_wrapper.remote.model.UploadPaste
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MitBinService {

    @GET("paste")
    suspend fun getPaste(@Query("name") name: String): List<Paste>

    @POST("paste")
    suspend fun addPaste(@Body paste: UploadPaste): Paste
}