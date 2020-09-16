package com.kriticalflare.bin_wrapper.data

import com.kriticalflare.bin_wrapper.remote.MitBinService
import com.kriticalflare.bin_wrapper.remote.model.Paste

class PasteRepository(private val pasteService: MitBinService): Repository {

    override suspend fun getPaste(name: String): List<Paste> = pasteService.getPaste(name)

    override suspend fun addPaste(paste: Paste): PasteResponse {
        return try {
            pasteService.addPaste(paste)
            Success
        } catch (t: Throwable){
            Failure(t.message ?: "Try again error later")
        }
    }

}

sealed class PasteResponse

object Success: PasteResponse()
class Failure(val message: String): PasteResponse()