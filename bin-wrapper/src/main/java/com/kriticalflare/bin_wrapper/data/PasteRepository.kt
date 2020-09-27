package com.kriticalflare.bin_wrapper.data

import com.kriticalflare.bin_wrapper.remote.MitBinService
import com.kriticalflare.bin_wrapper.remote.model.Paste
import com.kriticalflare.bin_wrapper.remote.model.UploadPaste

class PasteRepository(private val pasteService: MitBinService): Repository {

    override suspend fun getPaste(name: String): List<Paste> = pasteService.getPaste(name)

    override suspend fun addPaste(paste: UploadPaste): PasteResponse {
        return try {
            pasteService.addPaste(paste)
            PasteResponse.Success
        } catch (t: Throwable){
            PasteResponse.Failure(t.message ?: "Try again error later")
        }
    }

}

sealed class PasteResponse {
    object Success: PasteResponse()
    class Failure(val message: String): PasteResponse()
}

